package ru.shtykin.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class DayDto(
    @SerializedName("maxtemp_c") val maxTempC: Float,
    @SerializedName("mintemp_c") val minTempC: Float,
    @SerializedName("maxwind_kph") val maxWindKph: Float,
    @SerializedName("condition") val condition: ConditionDto,
    @SerializedName("avghumidity") val avghumidity: Float,

)