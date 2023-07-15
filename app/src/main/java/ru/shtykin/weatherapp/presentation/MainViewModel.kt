package ru.shtykin.weatherapp.presentation

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
import ru.shtykin.weatherapp.domain.entity.DayWeather
import ru.shtykin.weatherapp.domain.entity.ForecastWeather
import ru.shtykin.weatherapp.domain.usecase.DeleteAllForecastsFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.DeleteCityFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllCitiesFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetAllForecastsFromDbUseCase
import ru.shtykin.weatherapp.domain.usecase.GetCurrentWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetFlowAllCitiesWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.GetForecastWeatherUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertCityToDbUseCase
import ru.shtykin.weatherapp.domain.usecase.InsertForecastToDbUseCase
import ru.shtykin.weatherapp.presentation.state.ScreenState
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
    private val getForecastWeatherUseCase: GetForecastWeatherUseCase,
    private val insertCityToDbUseCase: InsertCityToDbUseCase,
    private val deleteCityFromDbUseCase: DeleteCityFromDbUseCase,
    private val getAllCitiesFromDbUseCase: GetAllCitiesFromDbUseCase,
    private val getFlowAllCitiesWeatherUseCase: GetFlowAllCitiesWeatherUseCase,
    private val insertForecastToDbUseCase: InsertForecastToDbUseCase,
    private val deleteAllForecastsFromDbUseCase: DeleteAllForecastsFromDbUseCase,
    private val getAllForecastsFromDbUseCase: GetAllForecastsFromDbUseCase
) : ViewModel() {

    private val _uiState =
        mutableStateOf<ScreenState>(
            ScreenState.MainScreen(
                currentWeather = null,
                isUpdateWeather = false,
                errorUpdate = null,
                cities = null,
                toggleUpdateList = false,
                forecastsWeather = null,
                forecasts = null
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
                getForecastWeather(currentCity)
            } catch (e: Exception) {
                _uiState.value = ScreenState.MainScreen(
                    currentWeather = null,
                    isUpdateWeather = false,
                    errorUpdate = "Список городов пуст",
                    cities = null,
                    toggleUpdateList = false,
                    forecastsWeather = null,
                    forecasts = null
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
            toggleUpdateList = false,
            forecastsWeather = null,
            forecasts = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val currentCity = getAllCitiesFromDb()[0]
                getWeather(currentCity)
                getAllCitiesWeather()
                getForecastWeather(currentCity)
            } catch (e: Exception) {
                _uiState.value = ScreenState.MainScreen(
                    currentWeather = null,
                    isUpdateWeather = false,
                    errorUpdate = "Список городов пуст",
                    cities = null,
                    toggleUpdateList = false,
                    forecastsWeather = null,
                    forecasts = null
                )
            }
        }
    }

    fun showForecast(city:String, day: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val forecastsWeather = getAllForecastsFromDb().find { it.day == day && it.city == city }
            _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                forecastsWeather = forecastsWeather
            )
        }
    }

    fun getForecastWeather(city: String) {
        _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
            forecasts = null
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val forecastWeather = getForecastWeather(city, FORECAST_DAYS)
                replaceForecast(forecastWeather)
                val listDaysWeather = mutableListOf<DayWeather>()
                forecastWeather.forEach {
                    listDaysWeather.add(DayWeather(
                        city = city,
                        day = it.day,
                        temperature = it.temperature,
                        iconUrl = it.iconUrl,
                        isError = false,
                        isUpdate = false
                    ))
                }
                _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                    forecasts = listDaysWeather
                )
            } catch (e: Exception) {

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
                _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                    toggleUpdateList = toggleUpdater,
                )
            }
        }
    }

    fun getWeather(city: String) {
        if (_uiState.value is ScreenState.MainScreen) {
            _uiState.value = (_uiState.value as ScreenState.MainScreen).copy(
                isUpdateWeather = true,
                forecastsWeather = null,
            )
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

    private suspend fun replaceForecast(forecastWeather: List<ForecastWeather>) {
        deleteAllForecastsFromDb()
        forecastWeather.forEach { insertForecastToDb(it) }
    }

    private suspend fun getCurrentWeather(city: String) = getCurrentWeatherUseCase.execute(city)

    private suspend fun getForecastWeather(city: String, days: Int) = getForecastWeatherUseCase.execute(city, days)

    private suspend fun insertCityToDb(name: String) = insertCityToDbUseCase.execute(name)

    private suspend fun deleteCityFromDb(name: String) = deleteCityFromDbUseCase.execute(name)

    private suspend fun getAllCitiesFromDb() = getAllCitiesFromDbUseCase.execute()

    private suspend fun getFlowAllCitiesWeatherUseCase() = getFlowAllCitiesWeatherUseCase.execute()

    private suspend fun insertForecastToDb(forecastWeather: ForecastWeather) = insertForecastToDbUseCase.execute(forecastWeather)

    private suspend fun deleteAllForecastsFromDb() = deleteAllForecastsFromDbUseCase.execute()

    private suspend fun getAllForecastsFromDb() = getAllForecastsFromDbUseCase.execute()

    companion object {
        private const val FORECAST_DAYS = 10
    }
}