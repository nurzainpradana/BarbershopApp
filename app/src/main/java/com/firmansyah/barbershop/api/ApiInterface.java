package com.firmansyah.barbershop.api;

import com.firmansyah.barbershop.model.Result;
import com.firmansyah.barbershop.model.ResultBarbershop;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface {

    @GET("barbershop/getAllBarber.php")
    Call<ResultBarbershop> getAllbarber();

    @FormUrlEncoded
    @POST("barbershop/getSearchBarbershop.php")
    Call<ResultBarbershop> getSearchBarber(
            @Field("kata_kunci") String kata_kunci);

    @GET("favorite/getFavorite.php")
    Call<ResultBarbershop> getFavorite();

    //Add to Favorite
    @Multipart
    @POST("favorite/add_to_favorite.php")
    Call<Result> addtoFavorite(
            @Part("id_barber") Integer id_barber);


    @FormUrlEncoded
    @POST("favorite/check_favorite.php")
    Call<Result> checkFavorite(
            @Field("id_barber") Integer id_barber);

    @Multipart
    @POST("favorite/delete_from_favorite.php")
    Call<Result> deleteFromFavorite(
            @Part("id_barber") Integer id_barber);

}
