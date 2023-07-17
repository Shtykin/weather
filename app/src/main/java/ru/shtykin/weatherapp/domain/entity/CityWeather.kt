package ru.shtykin.weatherapp.domain.entity

data class CityWeather(
    val name: String,
    var temperature: Int?,
    var iconUrl: String,
    val isError: Boolean,
    var isUpdate: Boolean
)
