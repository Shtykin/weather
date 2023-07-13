package ru.shtykin.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.shtykin.weatherapp.navigation.AppNavGraph
import ru.shtykin.weatherapp.navigation.Screen
import ru.shtykin.weatherapp.presentation.screen.main.MainScreen
import ru.shtykin.weatherapp.presentation.screen.settings.SettingsScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            val scope = rememberCoroutineScope()
            val uiState by viewModel.uiState
            val startScreenRoute = Screen.Main.route
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                AppNavGraph(
                    startScreenRoute = startScreenRoute,
                    navHostController = navHostController,
                    mainScreenContent = {
                        MainScreen(
                            uiState = uiState,
                            onGetWeatherClick = {viewModel.getWeather("Irkutsk")}
                        )
                    },
                    settingsScreenContent = {
                        SettingsScreen(
                            uiState = uiState,
//                            onGraphClick = {
//                                navHostController.navigate(Screen.Graph.route)
//                                viewModel.openGraphScreen()
//                            }
                        )
                    },
                )
            }

        }
    }
}