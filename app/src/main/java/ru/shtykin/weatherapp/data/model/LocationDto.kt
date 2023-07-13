package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class LocationDto(
    @SerializedName("name") val name: String,
    @SerializedName("region") val region: String
)