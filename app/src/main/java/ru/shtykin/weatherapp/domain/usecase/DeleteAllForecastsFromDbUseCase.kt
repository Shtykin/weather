package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.entity.ForecastWeather

class DeleteAllForecastsFromDbUseCase(private val repository: Repository) {
    suspend fun execute() = repository.deleteAllForecastsFromDb()
}