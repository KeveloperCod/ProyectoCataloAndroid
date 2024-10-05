package pe.com.proyectocatalogo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import pe.com.proyectocatalogo.adapter.ObjetosAdapter;
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


    //aqui enlazo mi objeto RecyclerView con mi componente que esta en activity_main.xml
// luego configuro la configuracion de como quiero que se vea en el Recycler ejemplo vista de grilla
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_main);
    recyclerView=findViewById(R.id.rv_movies);
    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));

    showMovies();

    }

    public void showMovies(){
        Call<List<Objetos>> call= ApiClient.getClient().create(ApiObjeto.class).getObjetos();
    call.enqueue(new Callback<List<Objetos>>() {
        @Override
        public void onResponse(Call<List<Objetos>> call, Response<List<Objetos>> response) {
            if (response.isSuccessful()){
                movies=response.body();
                movieAdapter=new ObjetosAdapter(movies,getApplicationContext());
        recyclerView.setAdapter(movieAdapter);
            }
        }

        @Override
        public void onFailure(Call<List<Objetos>> call, Throwable t) {
            Toast.makeText(MainActivity1.this, "ERROR DE CONEXION", Toast.LENGTH_SHORT).show();
        }
    });
    }



}
