package pe.com.proyectocatalogo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import java.util.List;

import pe.com.proyectocatalogo.adapter.ObjetosAdapter;
import pe.com.proyectocatalogo.model.LoginResponse;
import pe.com.proyectocatalogo.model.Objetos;
import pe.com.proyectocatalogo.network.ApiClient;
import pe.com.proyectocatalogo.network.ApiObjeto;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity1 extends AppCompatActivity {

    private List<Objetos> movies;
    private RecyclerView recyclerView;
    private ObjetosAdapter movieAdapter;

    // Variables para el login
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private GoogleSignInClient mGoogleSignInClient;

    private static final int RC_SIGN_IN = 9001; // Código de solicitud de inicio de sesión

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_login); // Primero muestra el layout de login

        // Configuración para el login de Google
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("479122015590-kag2b69vm1kd3tc0g8c4q4ntj8ksdlhd.apps.googleusercontent.com")
                .requestEmail()  // Solicitar el correo del usuario
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Configuración para el login
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser(); // Llamar a la función de login
            }
        });

        // Configurar el botón de Google Sign-In
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn(); // Llamar a tu método para iniciar el flujo de inicio de sesión
            }
        });
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(MainActivity1.this, "Por favor, completa los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiObjeto apiObjeto = ApiClient.getClient().create(ApiObjeto.class);
        Call<LoginResponse> call = apiObjeto.loginUser(email, password);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getStatus().equals("success")) {
                        Toast.makeText(MainActivity1.this, "Login exitoso", Toast.LENGTH_SHORT).show();
                        showMainScreen();  // Cambiar al layout principal
                        showMovies();  // Mostrar las películas después del login exitoso
                    } else {
                        Toast.makeText(MainActivity1.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(MainActivity1.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMainScreen() {
        setContentView(R.layout.activity_main); // Cambiar al layout principal después del login exitoso

        // Inicializar RecyclerView aquí, después de cambiar el layout
        recyclerView = findViewById(R.id.rv_movies);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        } else {
            Toast.makeText(this, "Error al encontrar el RecyclerView", Toast.LENGTH_SHORT).show();
        }
    }

    public void showMovies() {
        Call<List<Objetos>> call = ApiClient.getClient().create(ApiObjeto.class).getObjetos();
        call.enqueue(new Callback<List<Objetos>>() {
            @Override
            public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {
                if (response.isSuccessful()) {
                    movies = response.body();
                    // Verifica si movies no es null antes de crear el adaptador
                    if (movies != null) {
                        movieAdapter = new ObjetosAdapter(movies, getApplicationContext());
                        recyclerView.setAdapter(movieAdapter);
                    } else {
                        Toast.makeText(MainActivity1.this, "No se encontraron objetos", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Objetos>> call, Throwable t) {
                Toast.makeText(MainActivity1.this, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para iniciar el flujo de inicio de sesión de Google
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // Manejar el resultado del inicio de sesión de Google
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // El inicio de sesión fue exitoso, actualiza tu UI aquí
            updateUI(account);
        } catch (ApiException e) {
            // El inicio de sesión falló, actualiza tu UI aquí
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount account) {
        if (account != null) {
            // Usuario ha iniciado sesión exitosamente
            Toast.makeText(this, "Bienvenido, " + account.getDisplayName(), Toast.LENGTH_SHORT).show();
            // Aquí puedes navegar a la actividad principal
        } else {
            // Usuario no ha iniciado sesión
            Toast.makeText(this, "Error de inicio de sesión", Toast.LENGTH_SHORT).show();
        }
    }
}
