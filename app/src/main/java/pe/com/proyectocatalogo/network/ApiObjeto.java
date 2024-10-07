package pe.com.proyectocatalogo.network;

import java.util.List;

import pe.com.proyectocatalogo.model.LoginResponse;
import pe.com.proyectocatalogo.model.Objetos;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiObjeto {
    //Ahora especifico el metodo de envio http que permita obtener el json
    //en este caso uso el metodo de envio get a la ruta /movies/list.php
    @GET("/movies/list.php")

    //solo traigo la lista de los objetos en este caso pelis en el json
    Call<List<Objetos>> getObjetos();

    @FormUrlEncoded
    @POST("movies/login.php")
    Call<LoginResponse> loginUser(@Field("email") String email, @Field("password") String password);





}
