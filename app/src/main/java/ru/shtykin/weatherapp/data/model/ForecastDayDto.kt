package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class ForecastDayDto(
    @SerializedName("date") val date: String,
    @SerializedName("date_epoch") val dateEpoch: Long,
    @SerializedName("day") val day: DayDto,
)