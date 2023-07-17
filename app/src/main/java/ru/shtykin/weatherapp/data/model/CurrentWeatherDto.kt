package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrentWeatherDto(
    @SerializedName ("location") val location: LocationDto,
    @SerializedName ("current") val current: CurrentDto
)
