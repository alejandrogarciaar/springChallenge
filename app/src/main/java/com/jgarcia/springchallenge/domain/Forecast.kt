package com.jgarcia.springchallenge.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Forecast(
    val location: Location,
    val weather: Weather,
    val main: Main,
    val wind: Wind,
    val id: Long,
    val name: String
) {

    @Parcelize
    data class Location(
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