package com.draos.nekretnine.nekretnineui.Services;

import java.util.List;

import com.draos.nekretnine.nekretnineui.Model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    @GET("users/all")
    Call<List<User>> all();
    @GET("users/find/{userId}")
    Call<User> getUser(@Path("userId") long id);
    @POST("users/new")
    Call<ResponseBody> createUser(@Body User user);
    @POST("users/login")
    Call<ResponseBody> login(@Body User user);
    @PUT("users/{userId}")
    Call<ResponseBody> updateUser(@Path("userId") long id, @Body User user);

}