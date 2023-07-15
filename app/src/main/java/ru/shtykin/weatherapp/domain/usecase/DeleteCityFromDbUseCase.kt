package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository

class DeleteCityFromDbUseCase(private val repository: Repository) {
    suspend fun execute(name: String) = repository.deleteCityFromDb(name)
}