package ru.shtykin.weatherapp.presentation.state

import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.entity.CurrentWeather
import ru.shtykin.weatherapp.domain.entity.DayWeather
import ru.shtykin.weatherapp.domain.entity.ForecastWeather

sealed class ScreenState {

    data class MainScreen(
        val currentWeather: CurrentWeather?,
        val isUpdateWeather: Boolean,
        val errorUpdate: String?,
        val cities: List<CityWeather>?,
        val toggleUpdateList: Boolean,
        val forecastsWeather: ForecastWeather?,
        val forecasts: List<DayWeather>?
    ) : ScreenState()

    data class SettingsScreen(
        val cities: List<String>,
        val isLoading: Boolean
    ) : ScreenState()

}
