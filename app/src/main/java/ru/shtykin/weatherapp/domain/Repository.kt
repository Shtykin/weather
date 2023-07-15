package ru.shtykin.weatherapp.domain

import kotlinx.coroutines.flow.Flow
import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.entity.CurrentWeather

interface Repository {

    suspend fun getCurrentWeather(city: String): CurrentWeather
    suspend fun insertCityToDb(name: String)
    suspend fun deleteCityFromDb(name: String)
    suspend fun getAllCityFromDb(): List<String>
    suspend fun getFlowAllCitiesCurrentWeather(): Flow<CityWeather>
}