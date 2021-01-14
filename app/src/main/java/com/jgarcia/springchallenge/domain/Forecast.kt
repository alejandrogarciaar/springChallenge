package com.jgarcia.springchallenge.domain

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

data class Forecast(
    val location: Location,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val id: Long,
    val name: String
) {

    @Entity
    @Parcelize
    data class Location(
        @PrimaryKey(autoGenerate = true)
        val id: Int? = null,
        val longitude: Double,
        val latitude: Double
    ) : Parcelable

    data class Weather(
        val id: Long,
        val main: String,
        val description: String
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Double,
        val humidity: Int
    )

    data class Wind(
        val speed: Double,
        val deg: Int
    )
}