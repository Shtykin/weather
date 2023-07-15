package ru.shtykin.weatherapp.data.mapper

import ru.shtykin.weatherapp.data.db.model.ForecastDbModel
import ru.shtykin.weatherapp.data.model.CurrentWeatherDto
import ru.shtykin.weatherapp.data.model.ForecastWeatherDto
import ru.shtykin.weatherapp.domain.entity.CurrentWeather
import ru.shtykin.weatherapp.domain.entity.ForecastWeather
import java.text.SimpleDateFormat
import java.util.Locale

class Mapper {

    fun mapCurrentWeatherDtoToCurrentWeather(currentWeatherDto: CurrentWeatherDto) = CurrentWeather(
        city = currentWeatherDto.location.name,
        temperature = currentWeatherDto.current.tempC.toInt(),
        iconUrl = "https:" + currentWeatherDto.current.condition.icon,
        windMps = String.format("%.1f", (currentWeatherDto.current.windKph * METERS_IN_KILOMETERS / SECONDS_IN_HOUR)),
        pressureMmHg = (currentWeatherDto.current.pressureMb / MB_IN_MMHG).toInt(),
        text = currentWeatherDto.current.condition.text,
        lastUpdated = currentWeatherDto.current.lastUpdated
    )

    fun mapForecastWeatherDtoToForecastWeather(forecastWeatherDto: ForecastWeatherDto): List<ForecastWeather> {
        val forecastWeatherList = mutableListOf<ForecastWeather>()
        forecastWeatherDto.forecast.forecastday.forEach { forecastDayDto ->
            val sdf = SimpleDateFormat("d MMMM", Locale.getDefault())
            val date = java.util.Date(forecastDayDto.dateEpoch * 1000)


            forecastWeatherList.add(
                ForecastWeather(
                    day = sdf.format(date),
                    city = forecastWeatherDto.location.name,
                    temperature = "${forecastDayDto.day.minTempC.toInt()}..${forecastDayDto.day.maxTempC.toInt()}",
                    iconUrl = "https:" + forecastDayDto.day.condition.icon,
                    windMps = String.format("%.1f", (forecastDayDto.day.maxWindKph * METERS_IN_KILOMETERS / SECONDS_IN_HOUR)),
                    avghumidity = forecastDayDto.day.avghumidity.toInt().toString(),
                    text = forecastDayDto.day.condition.text
                ))
        }
        return forecastWeatherList
    }

    fun mapForecastWeatherToForecastDbModel(forecastWeather: ForecastWeather) = ForecastDbModel(
        day = forecastWeather.day,
        city = forecastWeather.city,
        temperature = forecastWeather.temperature,
        iconUrl = forecastWeather.iconUrl,
        windMps = forecastWeather.windMps,
        avghumidity = forecastWeather.avghumidity,
        text = forecastWeather.text
    )

    fun mapForecastDbModelToForecastWeather(forecastDbModel: ForecastDbModel) = ForecastWeather(
        day = forecastDbModel.day,
        city = forecastDbModel.city,
        temperature = forecastDbModel.temperature,
        iconUrl = forecastDbModel.iconUrl,
        windMps = forecastDbModel.windMps,
        avghumidity = forecastDbModel.avghumidity,
        text = forecastDbModel.text
    )

    companion object {
        const val MB_IN_MMHG = 1.33322
        const val SECONDS_IN_HOUR = 3600
        const val METERS_IN_KILOMETERS = 1000
    }


}