package pe.com.proyectocatalogo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import pe.com.proyectocatalogo.R;
import pe.com.proyectocatalogo.model.Objetos;

public class ObjetosAdapter extends RecyclerView.Adapter<ObjetosAdapter.ViewHolder> {
    // 2) Declare 2 variables, para que guarde el listado de peliculas y otra para acceder
    // al contexto del activity_main que muestra el componente RecyclerView

    private List<Objetos> movies;
    private Context context;

    //Luego creo un constructor para inicializar esos objetos declarados


    public ObjetosAdapter(List<Objetos> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }


    //Este metodo especificara el xml que se desea inflar en el componente RecyclerView

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Con este codigo especifico que quiero que sea el archivo item_movie.xml
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie,parent,false);
            return new ViewHolder(view);

    }

    //esta metodo coloca en item_movie.xml, la imagen de portada y el titulo de la pelicula de json
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_titulo.setText(movies.get(position).getTitulo());
        Glide.with(context).load(movies.get(position).getPortada()).into(holder.iv_portada);
    }

    //este metodo especifica la cantidad de item a devolver
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //1) agrege esta 2 variables para mostrar esos unicos datos del json
        private ImageView iv_portada;
        private TextView tv_titulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_portada=itemView.findViewById(R.id.iv_portada);
            tv_titulo=itemView.findViewById(R.id.tv_titulo);
        }
    }


}
