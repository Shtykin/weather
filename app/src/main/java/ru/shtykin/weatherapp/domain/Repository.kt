package ru.shtykin.weatherapp.domain

import ru.shtykin.weatherapp.domain.entity.CurrentWeather

interface Repository {

    suspend fun getCurrentWeather(city: String): CurrentWeather
}