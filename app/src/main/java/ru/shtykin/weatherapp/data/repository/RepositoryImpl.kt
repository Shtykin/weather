package ru.shtykin.weatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.shtykin.weatherapp.data.db.WeatherDataBase
import ru.shtykin.weatherapp.data.db.model.CityDbModel
import ru.shtykin.weatherapp.data.mapper.Mapper
import ru.shtykin.weatherapp.data.network.ApiService
import ru.shtykin.weatherapp.domain.Repository
import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.entity.CurrentWeather
import ru.shtykin.weatherapp.domain.entity.ForecastWeather

class RepositoryImpl(
    private val apiService: ApiService,
    private val mapper: Mapper,
    private val db: WeatherDataBase
) : Repository {

    override suspend fun getCurrentWeather(city: String): CurrentWeather {
        val response = apiService.getCurrentWeather(city, "ru")
        response.execute().body()?.let {
            return mapper.mapCurrentWeatherDtoToCurrentWeather(it)
        }
        throw IllegalStateException("Response body is empty")
    }

    override suspend fun getForecastWeather(city: String, days: Int): List<ForecastWeather> {
        val response = apiService.getForecastWeather(city, days.toString(), "ru")
        response.execute().body()?.let {
            return mapper.mapForecastWeatherDtoToForecastWeather(it)
        }
        throw IllegalStateException("Response body is empty")
    }

    override suspend fun insertCityToDb(name: String) {
        val cityDbModel = CityDbModel(name = name)
        db.getCityDao().insert(cityDbModel)
    }

    override suspend fun deleteCityFromDb(name: String) {
        val cityDbModel = CityDbModel(name = name)
        db.getCityDao().delete(cityDbModel)
    }

    override suspend fun getAllCityFromDb(): List<String> {
        return db.getCityDao().getAllCity().map { it.name }
    }

    override suspend fun getFlowAllCitiesCurrentWeather(): Flow<CityWeather> {
        return flow {
            getAllCityFromDb().forEach { city ->
                try {
                    val currentWeather = getCurrentWeather(city)
                    emit(
                        CityWeather(
                            name = currentWeather.city,
                            temperature = currentWeather.temperature,
                            iconUrl = currentWeather.iconUrl,
                            isError = false,
                            isUpdate = false
                        )
                    )
                } catch (e: Exception) {
                    emit(
                        CityWeather(
                            name = city,
                            temperature = null,
                            iconUrl = "",
                            isError = true,
                            isUpdate = false
                        )
                    )
                }
            }
        }
    }

    override suspend fun insertForecastToDb(forecastWeather: ForecastWeather) {
        val forecastDbModel = mapper.mapForecastWeatherToForecastDbModel(forecastWeather)
        db.getForecastDao().insert(forecastDbModel)
    }

    override suspend fun deleteAllForecastsFromDb() {
        db.getForecastDao().deleteAllForecasts()
    }

    override suspend fun getAllForecastsFromDb(): List<ForecastWeather> {
        return db.getForecastDao().getAllForecast().map { mapper.mapForecastDbModelToForecastWeather(it) }
    }
}