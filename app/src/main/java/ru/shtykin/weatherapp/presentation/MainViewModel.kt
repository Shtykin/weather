package ru.shtykin.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import ru.shtykin.weatherapp.presentation.screen.main.MainScreenState
import ru.shtykin.weatherapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase
) : ViewModel() {

    private val _uiState =
        mutableStateOf<ScreenState>(ScreenState.MainScreen(state = MainScreenState.Initial))

    val uiState: State<ScreenState>
        get() = _uiState




    fun getWeather(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
//            Log.e("DEBUG", "response -> ${getCurrentWeather(city)}")
            _uiState.value = ScreenState.MainScreen(MainScreenState.Weather(getCurrentWeather(city)))
        }
    }

    private suspend fun getCurrentWeather(city: String) = getCurrentWeatherUseCase.execute(city)
}