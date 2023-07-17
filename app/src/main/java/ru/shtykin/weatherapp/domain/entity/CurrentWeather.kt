package ru.shtykin.weatherapp.domain.entity

data class CurrentWeather(
    val city: String,
    val temperature: Int,
    val iconUrl: String,
    val windMps: String,
    val pressureMmHg: Int,
    val text: String,
    val lastUpdated: String,
    )
