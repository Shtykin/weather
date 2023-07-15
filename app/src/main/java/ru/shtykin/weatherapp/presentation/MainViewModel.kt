package ru.shtykin.weatherapp.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.usecase.DeleteCityFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllCitiesFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetFlowAllCitiesWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertCityToDbUseCase
import ru.shtykin.weatherapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val insertCityToDbUseCase: InsertCityToDbUseCase,
    private val deleteCityFromDbUseCase: DeleteCityFromDbUseCase,
    private val getAllCitiesFromDbUseCase: GetAllCitiesFromDbUseCase,
    private val getFlowAllCitiesWeatherUseCase: GetFlowAllCitiesWeatherUseCase
) : ViewModel() {

    private val _uiState =
        mutableStateOf<ScreenState>(
            ScreenState.MainScreen(
                currentWeather = null,
                isUpdateWeather = false,
                errorUpdate = null,
                cities = null,
                toggleUpdateList = false
            )
        )

    val uiState: State<ScreenState>
        get() = _uiState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (getAllCitiesFromDb().isEmpty()) {
                    insertCityToDb("Иркутск")
                    insertCityToDb("Москва")
                    insertCityToDb("Лондон")
                    insertCityToDb("Мадрид")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            delay(500)
            try {
                val currentCity = getAllCitiesFromDb()[0]
                getWeather(currentCity)
                getAllCitiesWeather()
            } catch (e: Exception) {
                _uiState.value = ScreenState.MainScreen(
                    currentWeather = null,
                    isUpdateWeather = false,
                    errorUpdate = "Список городов пуст",
                    cities = null,
                    toggleUpdateList = false
                )
            }
        }

    }

    fun openSettingsScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ScreenState.SettingsScreen(
                cities = getAllCitiesFromDb(),
                isLoading = true
            )
        }
    }

    fun openMainScreen() {
        _uiState.value = ScreenState.MainScreen(
            currentWeather = null,
            isUpdateWeather = false,
            errorUpdate = null,
            cities = null,
            toggleUpdateList = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentCity = getAllCitiesFromDb()[0]
                getWeather(currentCity)
                getAllCitiesWeather()
            } catch (e: Exception) {
                _uiState.value = ScreenState.MainScreen(
                    currentWeather = null,
                    isUpdateWeather = false,
                    errorUpdate = "Список городов пуст",
                    cities = null,
                    toggleUpdateList = false
                )
            }
        }
    }


    private fun getAllCitiesWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            val cityList = getAllCitiesFromDb()
            val listCitiesWeather = mutableListOf<CityWeather>()
            cityList.forEach {
                listCitiesWeather.add(
                    CityWeather(
                        name = it,
                        temperature = null,
                        iconUrl = "",
                        isError = false,
                        isUpdate = true
                    )
                )
            }
            _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                cities = listCitiesWeather
            )

            var toggleUpdater = true
            getFlowAllCitiesWeatherUseCase().collect { collect ->
                toggleUpdater = !toggleUpdater
                listCitiesWeather.find { it.name == collect.name }?.iconUrl = collect.iconUrl
                listCitiesWeather.find { it.name == collect.name }?.temperature =
                    collect.temperature
                listCitiesWeather.find { it.name == collect.name }?.isUpdate = false
                if (_uiState.value is ScreenState.MainScreen) {
                    Log.e("DEBUG", "list1 = $listCitiesWeather")
                    _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                        cities = listCitiesWeather,
                        toggleUpdateList = toggleUpdater,
                    )
                } else {
                    return@collect
                }
            }
            toggleUpdater = !toggleUpdater
            if (_uiState.value is ScreenState.MainScreen) {
                Log.e("DEBUG", "list1 = $listCitiesWeather")
                _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                    toggleUpdateList = toggleUpdater,
                )
            }
        }
    }

    fun getWeather(city: String) {
        if (_uiState.value is ScreenState.MainScreen) {
            _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(isUpdateWeather = true)
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentWeather = getCurrentWeather(city)
                if (_uiState.value is ScreenState.MainScreen) {
                    _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                        currentWeather = currentWeather,
                        isUpdateWeather = false,
                        errorUpdate = null
                    )
                }
            } catch (e: Exception) {
                if (_uiState.value is ScreenState.MainScreen) {
                    _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                        currentWeather = null,
                        isUpdateWeather = false,
                        errorUpdate = e.message
                    )
                }
            }
        }
    }

    fun addCity(
        city: String,
        onSuccess: ((String) -> Unit)? = null,
        onFailure: ((String) -> Unit)? = null
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val weather = getCurrentWeather(city)

                if (!(getAllCitiesFromDb().find { it == weather.city }.isNullOrEmpty())) {
                    withContext(Dispatchers.Main) { onFailure?.invoke("Такой город уже есть") }
                    return@launch
                }

                insertCityToDb(weather.city)
                if (_uiState.value is ScreenState.SettingsScreen) {
                    _uiState.value = (_uiState.value as ScreenState.SettingsScreen).copy(
                        cities = getAllCitiesFromDb()
                    )
                }
                withContext(Dispatchers.Main) { onSuccess?.invoke("${weather.city} добавлен") }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) { onFailure?.invoke("Не получилось найти такой город ¯\\_(ツ)_/¯") }
                return@launch
            }
        }
    }

    fun deleteCity(city: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCityFromDb(city)
            if (_uiState.value is ScreenState.SettingsScreen) {
                _uiState.value = (_uiState.value as ScreenState.SettingsScreen).copy(
                    cities = getAllCitiesFromDb()
                )
            }
        }
    }

    private suspend fun getCurrentWeather(city: String) = getCurrentWeatherUseCase.execute(city)

    private suspend fun insertCityToDb(name: String) = insertCityToDbUseCase.execute(name)

    private suspend fun deleteCityFromDb(name: String) = deleteCityFromDbUseCase.execute(name)

    private suspend fun getAllCitiesFromDb() = getAllCitiesFromDbUseCase.execute()

    private suspend fun getFlowAllCitiesWeatherUseCase() = getFlowAllCitiesWeatherUseCase.execute()
}