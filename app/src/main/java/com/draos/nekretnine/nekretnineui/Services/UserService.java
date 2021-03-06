package com.draos.nekretnine.nekretnineui.Services;

import java.util.List;

import com.draos.nekretnine.nekretnineui.Model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface UserService {
    @GET("users/all")
    Call<List<User>> all();
    @GET("users/find/{userId}")
    Call<User> getUser(@Path("userId") long id);
    @GET("users/{username}")
    Call<User> getUserByUsername(@Path("username") String username);
    @POST("users/new")
    Call<ResponseBody> createUser(@Body User user);
    @POST("users/login")
    Call<User> login(@Body User user);
    @PUT("users/{username}")
    Call<ResponseBody> updateUser(@Path("username") String username, @Body User user);
    @DELETE("users/{userId}")
    Call<ResponseBody> deleteUser(@Path("userId") long id);
}