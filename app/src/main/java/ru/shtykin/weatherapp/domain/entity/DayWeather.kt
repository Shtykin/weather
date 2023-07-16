package ru.shtykin.weatherapp.domain.entity

data class DayWeather(
    val city: String,
    val day: String,
    var temperature: String,
    var iconUrl: String,
    var text: String,
    val isError: Boolean,
    var isUpdate: Boolean
)
