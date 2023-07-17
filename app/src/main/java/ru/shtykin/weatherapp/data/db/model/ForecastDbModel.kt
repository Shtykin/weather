package ru.shtykin.weatherapp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "forecast")
data class ForecastDbModel(

    @PrimaryKey
    @ColumnInfo
    val day: String = "",

    @ColumnInfo
    val city: String = "",

    @ColumnInfo
    val temperature: String = "",

    @ColumnInfo
    val iconUrl: String = "",

    @ColumnInfo
    val windMps: String = "",

    @ColumnInfo
    val avghumidity: String = "",

    @ColumnInfo
    val text: String = "",

    )