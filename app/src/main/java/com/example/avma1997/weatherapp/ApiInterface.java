package com.example.avma1997.weatherapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Avma1997 on 11/6/2017.
 */
public interface ApiInterface{

    @GET("forecast")
    Call<WeatherResponse> getWeatherList(@Query("lat") String latitude, @Query("lon") String longitude, @Query("APPID") String api_key);
}
