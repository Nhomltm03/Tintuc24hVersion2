package com.example.tintuc24version2.WeatherAPI;

import com.example.tintuc24version2.WeatherModels.WeatherResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherInterface {

    @GET("weather")
    Call<WeatherResult> getWeatherByCityName(
            @Query("q") String cityName,
            @Query("units") String unit,
            @Query("appid") String apiKey
    );
}
