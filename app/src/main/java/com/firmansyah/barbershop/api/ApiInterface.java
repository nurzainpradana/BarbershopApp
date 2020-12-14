package com.firmansyah.barbershop.api;

import com.firmansyah.barbershop.model.Result;
import com.firmansyah.barbershop.model.ResultBarbershop;

import java.util.Date;
import java.util.List;

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

    @GET("favorite/getFavorite.php")
    Call<ResultBarbershop> getFavorite();

    @FormUrlEncoded
    @POST("api/user/removePhotoProfile.php")
    Call<Result> removePhotoProfile(@Field("filename") String filename);

    @FormUrlEncoded
    @POST("api/user/updateUser.php")
    Call<Result> updateUser(@Field("id_user") int id_user,
                            @Field("name") String name,
                            @Field("no_phone") String no_phone,
                            @Field("username") String username,
                            @Field("email") String email,
                            @Field("address") String address,
                            @Field("date_of_birth") Date date_of_birth,
                            @Field("photo_profile") String photo_profile);

    @FormUrlEncoded
    @POST("api/user/updatePassword.php")
    Call<Result> updatePassword(
            @Field("username") String username,
            @Field("password") String password);

    //Add to Cart
    @Multipart
    @POST("/api/cart/add_to_cart.php")
    Call<Result> addtocartcall(
            //@Part("securecode") String securecode,
            @Part("id_products") int id_products,
            @Part("id_user") int id_user,
            @Part("quantity") int quantity
    );

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

//
//    //get user wishlist
//    @Multipart
//    @POST("android/getuserwishlist.php")
//    Call<FavoriteDetail> getCartDetail(
//            @Part("securecode") RequestBody securecode,
//            @Part("id_member") RequestBody id_member
//    );

    @Multipart
    @POST("/api/cart/delete_cart.php")
    Call<Result> deleteCartCall(
            @Part("id_cart") int id_cart);

    @Multipart
    @POST("/api/cart/checkout.php")
    Call<Result> checkout(
            @Part("id_transaction") int id_transaction,
            @Part("id_user") int id_user,
            @Part("total") int total);

    @Multipart
    @POST("/api/cart/checkout_item.php")
    Call<Result> checkoutItem(
            @Part("id_transaction") int id_transaction,
            @Part("id_product") int id_product,
            @Part("harga_satuan") int harga_satuan,
            @Part("quantity") int quantity,
            @Part("subtotal") int subtotal);

}
