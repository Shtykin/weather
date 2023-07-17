package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository

class GetAllCitiesFromDbUseCase(private val repository: Repository) {
    suspend fun execute() = repository.getAllCityFromDb()
}