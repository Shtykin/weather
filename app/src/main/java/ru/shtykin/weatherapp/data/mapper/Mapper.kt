package ru.shtykin.weatherapp.data.mapper

import ru.shtykin.weatherapp.data.model.CurrentWeatherDto
import ru.shtykin.weatherapp.domain.entity.CurrentWeather

class Mapper {

    fun mapCurrentWeatherDtoToCurrentWeather(currentWeatherDto: CurrentWeatherDto) = CurrentWeather(
            city = currentWeatherDto.location.name,
            temperature = currentWeatherDto.current.tempC,
            iconUrl = currentWeatherDto.current.condition.icon,
            windMps = currentWeatherDto.current.windKph * MINUTES_IN_HOUR / METERS_IN_KILOMETERS,
            pressureMmHg = (currentWeatherDto.current.pressureMb / MB_IN_MMHG).toInt(),
            lastUpdated = currentWeatherDto.current.lastUpdated
        )

    companion object {
        const val MB_IN_MMHG = 1.33322
        const val MINUTES_IN_HOUR = 60
        const val METERS_IN_KILOMETERS = 1000
    }


}