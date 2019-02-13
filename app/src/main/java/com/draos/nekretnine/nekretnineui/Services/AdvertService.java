package com.draos.nekretnine.nekretnineui.Services;

import com.draos.nekretnine.nekretnineui.Model.AdvertSearch;
import com.draos.nekretnine.nekretnineui.Model.Advertise;
import com.draos.nekretnine.nekretnineui.Model.User;
import com.draos.nekretnine.nekretnineui.Model.UserAdvertInformation;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.ArrayList;
import java.util.List;

public interface AdvertService {
    @GET("adverts/all")
    Call<List<Advertise>> all();
    @GET("adverts/sale")
    Call<List<Advertise>> getSale();
    @GET("adverts/rent")
    Call<List<Advertise>> getRent();
    @GET("adverts/{advertId}")
    Call<Advertise> getAdvertise(@Path("advertId") long id);
    @GET("adverts/favorite/{userId}")
    Call<List<Advertise>> getFavorites(@Path("userId") long userId);
    @GET("adverts/getPostedBy/{userId}")
    Call<List<Advertise>> getAdvertsPostedBy(@Path("userId") long userId);
    @GET("adverts/userInfo/{userId}")
    Call<UserAdvertInformation> getUserInformation(@Path("userId") long id);
    @POST("adverts/post/favorite/{userId}/{advertId}")
    Call<ResponseBody> addFavorite(@Path("userId") long userId,@Path("advertId") long advertId);
    @Multipart
    @POST("adverts/post")
    Call<ResponseBody> createAdvert(@Part List<MultipartBody.Part> images, @Part("formDataJson") RequestBody formDataJson);
    @Multipart
    @POST("/uploadFile")
    Call<ResponseBody> postImage(@Part List<MultipartBody.Part> images);
    @PUT("adverts/plusView/{advertId}")
    Call<Advertise> updateViewsCount(@Path("advertId") long id);
    @Multipart
    @PUT("adverts/{advertId}")
    Call<ResponseBody> updateAdvert(@Path("advertId") long id,@Part List<MultipartBody.Part> images, @Part("formDataJson") RequestBody formDataJson);
    @DELETE("/adverts/{advertId}")
    Call<ResponseBody> deleteAdvert(@Path("advertId") long id);
    @DELETE("/adverts/favorite/{userId}/{advertId}")
    Call<ResponseBody> removeFavourite(@Path("userId") long userId, @Path("advertId") long advertId);
    @GET("adverts/favorite/{userId}/{advertId}")
    Call<ResponseBody> checkIfFavourite(@Path("userId") long userId, @Path("advertId") long advertId);
    @POST("adverts/search")
    Call<List<Advertise>> searchAdverts(@Body AdvertSearch search);
    @GET("/advertFiles/{advertId}")
    Call<ArrayList<String>> getAdvertFiles(@Path("advertId") long advertId);




}
