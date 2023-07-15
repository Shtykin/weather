package ru.shtykin.weatherapp.data.db.dao

import androidx.room.*
import ru.shtykin.weatherapp.data.db.model.CityDbModel

@Dao
interface CityDao {

    @Query("SELECT * from cities")
    fun getAllCity(): List<CityDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(city: CityDbModel)

    @Query("DELETE from cities")
    fun deleteAllCities()

    @Delete
    fun delete(city: CityDbModel)
}
