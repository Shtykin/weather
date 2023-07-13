package ru.shtykin.weatherapp.presentation.screen.main

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.SimCard
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import ru.shtykin.weatherapp.presentation.state.ScreenState

@Composable
fun MainScreen(
    uiState: ScreenState,
    onGetWeatherClick: (() -> Unit)?,
) {

    val weather =
        (((uiState as? ScreenState.MainScreen)?.state) as? MainScreenState.Weather)?.weather

    Column() {
        Button(onClick = { onGetWeatherClick?.invoke() }) {
            Text(text = "GETTTTTTTTTTTT")

        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            weather?.let {
                Row(
                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(modifier = Modifier.width(32.dp))
                    Text(
                        text = it.city,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    AsyncImage(
                        model = it.iconUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(96.dp)
                    )
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

                Row() {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = it.lastUpdated, fontSize = 12.sp, color = Color.Gray)
                    Spacer(modifier = Modifier.width(16.dp))
                }
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

@Composable
fun Element(
    name: String,
    imageVector: ImageVector,
    value: String,
    unit: String
){
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