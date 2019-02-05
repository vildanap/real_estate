package com.draos.nekretnine.nekretnineui.Services;

import com.draos.nekretnine.nekretnineui.Model.Advertise;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AdvertService {
    @GET("adverts/all")
    Call<List<Advertise>> all();
    @GET("adverts/{advertId}")
    Call<Advertise> getAdvertise(@Path("advertId") long id);
    @Multipart
    @POST("adverts/post")
    Call<ResponseBody> createAdvert(@Part MultipartBody.Part image, @Part("formDataJson") RequestBody formDataJson);

}