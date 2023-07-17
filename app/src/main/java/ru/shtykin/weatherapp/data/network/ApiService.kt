package ru.shtykin.weatherapp.data.network

import retrofit2.Call
import retrofit2.http.*
import ru.shtykin.weatherapp.data.model.CurrentWeatherDto
import ru.shtykin.weatherapp.data.model.ForecastWeatherDto


interface ApiService {

    @GET("v1/current.json?key=a407be13669f42cc8e3150053231307&&aqi=no")
    fun getCurrentWeather (
        @Query("q") city: String,
        @Query("lang") lang: String,
    ): Call<CurrentWeatherDto>

    @GET("v1/forecast.json?key=a407be13669f42cc8e3150053231307&&aqi=no")
    fun getForecastWeather (
        @Query("q") city: String,
        @Query("days") days: String,
        @Query("lang") lang: String,
    ): Call<ForecastWeatherDto>

}