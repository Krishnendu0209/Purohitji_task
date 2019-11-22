package com.example.weapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Mushtaq on 05-11-2018.
 */

public interface WeatherServiceApi {
    @GET("data/2.5/weather?") // Will get appended to the base_url
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String cityName, @Query("APPID") String app_id); // the request parameter
}
