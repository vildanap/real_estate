package com.draos.nekretnine.nekretnineui.Services;


import android.text.TextUtils;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RealEstateServiceGenerator {

   //private static final String BASE_URL = "http://10.0.2.2:8080/";
   //private static final String BASE_URL = "http://192.168.1.112:8080/";
    private static final String BASE_URL = "http://draos.herokuapp.com";

    private static Retrofit.Builder builder
            = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static OkHttpClient.Builder httpClient
            = new OkHttpClient.Builder();

    public static <S> S createService(Class<S> serviceClass) {
        return retrofit.create(serviceClass);
    }

}