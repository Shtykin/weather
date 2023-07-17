package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.entity.ForecastWeather

class InsertForecastToDbUseCase(private val repository: Repository) {
    suspend fun execute(forecastWeather: ForecastWeather) = repository.insertForecastToDb(forecastWeather)
}