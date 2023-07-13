package ru.shtykin.weatherapp.data.repository

import android.util.Log
import ru.shtykin.weatherapp.data.mapper.Mapper
import ru.shtykin.weatherapp.data.model.CityDto
import ru.shtykin.weatherapp.data.model.CurrentWeatherDto
import ru.shtykin.weatherapp.data.network.ApiService
import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.entity.CurrentWeather

class RepositoryImpl(
    private val apiService: ApiService,
    private val mapper: Mapper
): Repository {

    override suspend fun getCurrentWeather(city: String): CurrentWeather {
        val response = apiService.getCurrentWeather(city)
        response.execute().body()?.let {
           return mapper.mapCurrentWeatherDtoToCurrentWeather(it)
        }
        throw IllegalStateException("Response body is empty")
    }
}