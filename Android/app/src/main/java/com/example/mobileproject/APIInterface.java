package com.example.mobileproject;


import retrofit2.Call;
import retrofit2.http.GET;

public interface APIInterface {

    @GET("api/master/user/user")
    Call<Object> getUser();
}
