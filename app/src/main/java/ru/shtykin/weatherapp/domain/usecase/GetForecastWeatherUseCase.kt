package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository

class GetForecastWeatherUseCase(private val repository: Repository) {
    suspend fun execute(city: String, days: Int) = repository.getForecastWeather(city, days)
}