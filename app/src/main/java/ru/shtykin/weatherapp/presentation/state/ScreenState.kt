package ru.shtykin.weatherapp.presentation.state

import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.entity.CurrentWeather

sealed class ScreenState {

    data class MainScreen(
        val currentWeather: CurrentWeather?,
        val isUpdateWeather: Boolean,
        val errorUpdate: String?,
        val cities: List<CityWeather>?,
        val toggleUpdateList: Boolean
    ) : ScreenState()

    data class SettingsScreen(
        val cities: List<String>,
        val isLoading: Boolean
    ) : ScreenState()

}
