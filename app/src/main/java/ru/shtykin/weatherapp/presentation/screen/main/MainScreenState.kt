package ru.shtykin.weatherapp.presentation.screen.main

import ru.shtykin.weatherapp.domain.entity.CurrentWeather

sealed class MainScreenState {

    object Initial: MainScreenState()

    data class Weather(
        val weather: CurrentWeather
    ): MainScreenState()
}
