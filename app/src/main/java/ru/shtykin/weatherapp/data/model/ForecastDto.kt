package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastDto(
    @SerializedName("forecastday") val forecastday: List<ForecastDayDto>
)