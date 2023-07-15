package ru.shtykin.weatherapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.shtykin.weatherapp.data.db.dao.CityDao
import ru.shtykin.weatherapp.data.db.model.CityDbModel

@Database(entities = [CityDbModel::class], version = 1)
abstract class WeatherDataBase : RoomDatabase(){

    abstract fun getCityDao(): CityDao
}