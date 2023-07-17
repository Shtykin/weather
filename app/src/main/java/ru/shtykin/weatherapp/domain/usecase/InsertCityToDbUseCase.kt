package ru.shtykin.weatherapp.domain.usecase

import ru.shtykin.weatherapp.domain.Repository

class InsertCityToDbUseCase(private val repository: Repository) {
    suspend fun execute(name: String) = repository.insertCityToDb(name)
}