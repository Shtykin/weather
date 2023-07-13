package ru.shtykin.weatherapp.data.mapper

import ru.shtykin.weatherapp.data.model.CurrentWeatherDto
import ru.shtykin.weatherapp.domain.entity.CurrentWeather

class Mapper {

    fun mapCurrentWeatherDtoToCurrentWeather(currentWeatherDto: CurrentWeatherDto) = CurrentWeather(
        city = currentWeatherDto.location.name,
        temperature = currentWeatherDto.current.tempC,
        iconUrl = "https:" + currentWeatherDto.current.condition.icon,
        windMps = String.format("%.1f", (currentWeatherDto.current.windKph * METERS_IN_KILOMETERS / SECONDS_IN_HOUR)),
        pressureMmHg = (currentWeatherDto.current.pressureMb / MB_IN_MMHG).toInt(),
        lastUpdated = currentWeatherDto.current.lastUpdated
    )

    companion object {
        const val MB_IN_MMHG = 1.33322
        const val SECONDS_IN_HOUR = 3600
        const val METERS_IN_KILOMETERS = 1000
    }


}