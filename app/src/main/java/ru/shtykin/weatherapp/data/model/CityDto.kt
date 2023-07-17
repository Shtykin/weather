package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName
import retrofit2.http.Path

data class CityDto(
    @Path("city") val city: String
)