package ru.shtykin.weatherapp.navigation

sealed class Screen(
    val route: String
) {
    object Main: Screen(ROUTE_MAIN)
    object Settings: Screen(ROUTE_SETTINGS)

    private companion object {
        const val ROUTE_MAIN = "main"
        const val ROUTE_SETTINGS = "settings"
    }
}
