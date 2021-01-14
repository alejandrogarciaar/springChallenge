package com.jgarcia.springchallenge.remoteData.model

import com.google.gson.annotations.SerializedName

data class RemoteWeatherResponse(
    @SerializedName("coord")
    val coordinates: RemoteCoordinates,
    @SerializedName("weather")
    val weather: RemoteWeather,
    @SerializedName("main")
    val main: RemoteMain,
    @SerializedName("wind")
    val wind: RemoteWind,
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String
) {
    data class RemoteCoordinates(
        @SerializedName("lon")
        val longitude: Double,
        @SerializedName("lan")
        val latitude: Double
    )

    data class RemoteWeather(
        @SerializedName("id")
        val id: Long,
        @SerializedName("main")
        val main: String,
        @SerializedName("description")
        val description: String,
        @SerializedName("icon")
        val icon: String
    )

    data class RemoteMain(
        @SerializedName("temp")
        val temp: Double,
        @SerializedName("feels_like")
        val feels_like: Double,
        @SerializedName("temp_min")
        val temp_min: Double,
        @SerializedName("temp_max")
        val temp_max: Double,
        @SerializedName("pressure")
        val pressure: Double,
        @SerializedName("humidity")
        val humidity: Int
    )

    data class RemoteWind(
        @SerializedName("speed")
        val speed: Double,
        @SerializedName("deg")
        val deg: Int
    )
}