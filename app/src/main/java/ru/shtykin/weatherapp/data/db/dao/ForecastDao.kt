package ru.shtykin.weatherapp.data.db.dao

import androidx.room.*
import ru.shtykin.weatherapp.data.db.model.ForecastDbModel

@Dao
interface ForecastDao {

    @Query("SELECT * from forecast")
    fun getAllForecast(): List<ForecastDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(forecast: ForecastDbModel)

    @Query("DELETE from forecast")
    fun deleteAllForecasts()

    @Delete
    fun delete(forecast: ForecastDbModel)
}
