package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastWeatherDto(
    @SerializedName ("location") val location: LocationDto,
    @SerializedName ("current") val current: CurrentDto,
    @SerializedName ("forecast") val forecast: ForecastDto
)
