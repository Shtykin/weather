package ru.shtykin.weatherapp.domain.entity

data class CurrentWeather(
    val city: String,
    val temperature: Float,
    val iconUrl: String,
    val windMps: String,
    val pressureMmHg: Int,
    val lastUpdated: String,
    )
