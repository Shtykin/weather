package ru.shtykin.weatherapp.presentation.state

import ru.shtykin.weatherapp.presentation.screen.main.MainScreenState
import ru.shtykin.weatherapp.presentation.screen.settings.SettingsScreenState

sealed class ScreenState {

    data class MainScreen(
        val state: MainScreenState
    ) : ScreenState()

    data class SettingsScreen(
        val state: SettingsScreenState
    ) : ScreenState()

}
