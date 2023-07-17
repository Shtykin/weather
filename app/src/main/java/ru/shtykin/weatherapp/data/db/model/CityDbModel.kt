package ru.shtykin.weatherapp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityDbModel(

    @PrimaryKey
    @ColumnInfo
    val name: String = ""

)