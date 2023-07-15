package ru.shtykin.weatherapp.domain.entity

data class ForecastWeather(
    val day: String,
    val city: String,
    val temperature: String,
    val iconUrl: String,
    val windMps: String,
    val avghumidity: String,
    val text: String,
    )
