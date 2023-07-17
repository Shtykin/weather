package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrentDto(
    @SerializedName("last_updated") val lastUpdated: String,
    @SerializedName("temp_c") val tempC: Float,
    @SerializedName("condition") val condition: ConditionDto,
    @SerializedName("wind_kph") val windKph: Float,
    @SerializedName("pressure_mb") val pressureMb: Float,
)