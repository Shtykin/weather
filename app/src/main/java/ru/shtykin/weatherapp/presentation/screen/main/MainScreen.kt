package ru.shtykin.weatherapp.presentation.screen.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import ru.shtykin.weatherapp.presentation.state.ScreenState

@Composable
fun MainScreen(
    uiState: ScreenState,
    onGetWeatherClick: (() -> Unit)?,
) {
    
    val weather = (((uiState as? ScreenState.MainScreen)?.state) as? MainScreenState.Weather)?.weather

    Column(){
        Button(onClick = { onGetWeatherClick?.invoke() }) {
            Text(text = "GETTTTTTTTTTTT")

        }
        Card() {
            weather?.let { 
                Text(text = it.city)
                Text(text = it.lastUpdated)
                Text(text = it.temperature.toString())
                Text(text = it.windMps.toString())
                Text(text = it.pressureMmHg.toString())
                Image(
                    modifier = Modifier.size(64.dp),
                    painter = rememberImagePainter(it.iconUrl),
                    contentDescription = null
                )
                Log.e("DEBUG", "url -> ${it.iconUrl}")
            }
        }
        LazyRow {
            item {
                Text(text = "Тут будет список городов")
            }
        }
        LazyRow {
            item {
                Text(text = "Тут будет выбор дней")
            }
        }
    }
}