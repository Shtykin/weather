package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository

class GetCurrentWeatherUseCase(private val repository: Repository) {
    suspend fun execute(city: String) = repository.getCurrentWeather(city)
}