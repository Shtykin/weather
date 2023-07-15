package ru.shtykin.weatherapp.presentation.screen.main

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.shtykin.weatherapp.domain.entity.CityWeather
import ru.shtykin.weatherapp.domain.entity.CurrentWeather
import ru.shtykin.weatherapp.presentation.state.ScreenState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    uiState: ScreenState,
//    onSwipeDownWeatherCard: (() -> Unit)?,
    onCityClick: ((String) -> Unit)?,
    onAddClick: (() -> Unit)?,
    onDayClick: ((List<String>) -> Unit)?
) {

    val currentWeather = (uiState as? ScreenState.MainScreen)?.currentWeather
    var weather: CurrentWeather? by remember {
        mutableStateOf(null)
    }
    if (currentWeather != null) weather = currentWeather

    val cities = (uiState as? ScreenState.MainScreen)?.cities
    var citiesWeather: List<CityWeather>? by remember {
        mutableStateOf((uiState as? ScreenState.MainScreen)?.cities)
    }
    citiesWeather = cities

    val forecasts = (uiState as? ScreenState.MainScreen)?.forecasts

    val forecastWeather = (uiState as? ScreenState.MainScreen)?.forecastsWeather

    val isWeatherUpdate = (uiState as? ScreenState.MainScreen)?.isUpdateWeather ?: false
    val errorUpdate = (uiState as? ScreenState.MainScreen)?.errorUpdate

    Scaffold() {
        Column() {
            Spacer(modifier = Modifier.height(20.dp))
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(250.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                if (forecastWeather == null) {
                    if (errorUpdate != null) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Text(
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .padding(16.dp),
                                text = "Oops...\n$errorUpdate"
                            )
                        }
                    } else {
                        weather?.let {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = it.iconUrl,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(CircleShape)
                                        .size(48.dp)
                                )
                                Column() {
                                    Text(
                                        text = it.city,
                                        color = Color.Black,
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = it.text)
                                }
                                Spacer(modifier = Modifier.weight(1f))
                                if (isWeatherUpdate) CircularProgressIndicator()

                            }
                            Element(
                                name = "Температура",
                                imageVector = Icons.Default.Thermostat,
                                value = it.temperature.toString(),
                                unit = " °C"
                            )
                            Element(
                                name = "Ветер",
                                imageVector = Icons.Default.Air,
                                value = it.windMps.toString(),
                                unit = " м/с"
                            )
                            Element(
                                name = "Давление",
                                imageVector = Icons.Default.Compress,
                                value = it.pressureMmHg.toString(),
                                unit = " мм.рт.ст"
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Row() {
                                Spacer(modifier = Modifier.weight(1f))
                                Text(text = it.lastUpdated, fontSize = 12.sp, color = Color.Gray)
                                Spacer(modifier = Modifier.width(16.dp))
                            }
                        }
                        if (weather == null) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                CircularProgressIndicator(Modifier.align(Alignment.Center))
                            }
                        }
                    }
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = forecastWeather.iconUrl,
                            contentDescription = null,
                            modifier = Modifier
                                .clip(CircleShape)
                                .size(48.dp)
                        )
                        Column() {
                            Text(
                                text = forecastWeather.city,
                                color = Color.Black,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(text = forecastWeather.text)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = forecastWeather.day,
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    Element(
                        name = "Температура",
                        imageVector = Icons.Default.Thermostat,
                        value = forecastWeather.temperature,
                        unit = " °C"
                    )
                    Element(
                        name = "Ветер",
                        imageVector = Icons.Default.Air,
                        value = forecastWeather.windMps,
                        unit = " м/с"
                    )
                    Element(
                        name = "Влажность",
                        imageVector = Icons.Default.WaterDrop,
                        value = forecastWeather.avghumidity,
                        unit = " %"
                    )
                }

            }
            Spacer(modifier = Modifier.height(10.dp))
            LazyRow {
                weather?.let { currentWeather ->
                    item {
                        ForecastMiniElement(city = currentWeather.city,
                            day = "Сейчас",
                            temperature = "${currentWeather.temperature}°C",
                            iconUrl = currentWeather.iconUrl,
                            isError = false,
                            isUpdate = isWeatherUpdate,
                            onDayClick = { onCityClick?.invoke(currentWeather.city) })
                    }
                }

                forecasts?.let { list ->
                    items(list) {
                        ForecastMiniElement(
                            city = it.city,
                            day = it.day,
                            temperature = it.temperature + "°C",
                            iconUrl = it.iconUrl,
                            isError = it.isError,
                            isUpdate = it.isUpdate,
                            onDayClick = onDayClick
                        )
                    }
                }
                if (forecasts == null) {
                    items(10) {
                        ForecastMiniElement(city = currentWeather?.city ?: "",
                            day = "",
                            temperature = "",
                            iconUrl = "",
                            isError = false,
                            isUpdate = true,
                            onDayClick = {})
                    }

                }
            }
            Spacer(modifier = Modifier.weight(1f))
            LazyRow {
                item { AddCityButton(onAddClick = onAddClick) }
                citiesWeather?.let { list ->
                    items(list) {
                        CityElement(
                            name = it.name,
                            temperature = if (it.temperature != null) "${it.temperature}°C" else "",
                            iconUrl = it.iconUrl,
                            isError = it.isError,
                            isUpdate = it.isUpdate,
                            onCityClick = onCityClick
                        )
                    }
                    if (list.size > 7) {
                        item { AddCityButton(onAddClick = onAddClick) }
                    }
                }

            }
            Spacer(modifier = Modifier.height(20.dp))
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    text ="using Weather Api",
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
            Row() {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier.padding(end = 4.dp),
                    text ="by Evgenii Shtykin",
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }

}

@Composable
fun AddCityButton(
    onAddClick: (() -> Unit)?
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .clickable { onAddClick?.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp),
                text = "Добавить",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 4.dp),
                imageVector = Icons.Default.LocationCity,
                contentDescription = "Localized description",
                tint = Color.Black
            )
        }
    }
}

@Composable
fun ForecastMiniElement(
    city: String,
    day: String,
    temperature: String,
    iconUrl: String,
    isError: Boolean,
    isUpdate: Boolean,
    onDayClick: ((List<String>) -> Unit)?
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .clickable { onDayClick?.invoke(listOf(city, day)) },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp),
                text = day,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (isUpdate) {
                CircularProgressIndicator(
                    Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            } else {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp),
                text = temperature,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun CityElement(
    name: String,
    temperature: String,
    iconUrl: String,
    isError: Boolean,
    isUpdate: Boolean,
    onCityClick: ((String) -> Unit)?
) {
    Card(
        modifier = Modifier
            .size(100.dp)
            .padding(8.dp)
            .clickable { onCityClick?.invoke(name) },
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 4.dp),
                text = name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            if (isUpdate) {
                CircularProgressIndicator(
                    Modifier
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            } else {
                AsyncImage(
                    model = iconUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(32.dp)
                        .align(Alignment.Center)
                )
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 4.dp),
                text = temperature,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
fun Element(
    name: String, imageVector: ImageVector, value: String, unit: String
) {
    Row(modifier = Modifier.padding(16.dp)) {
        Icon(
            imageVector = imageVector,
            contentDescription = "Localized description",
            tint = Color.Black
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = name, fontSize = 16.sp, color = Color.Gray)
        Spacer(modifier = Modifier.weight(1f))
        Text(text = value, fontSize = 18.sp, fontWeight = FontWeight.SemiBold)
        Text(text = unit, fontSize = 18.sp)
    }
}