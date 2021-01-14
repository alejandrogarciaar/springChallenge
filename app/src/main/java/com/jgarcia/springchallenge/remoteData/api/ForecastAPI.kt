package com.jgarcia.springchallenge.remoteData.api

import com.jgarcia.springchallenge.remoteData.model.RemoteForecastResponse
import com.jgarcia.springchallenge.remoteData.util.Url
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET(Url.WEATHER_PATH)
    suspend fun getWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Url.API_KEY
    ): RemoteForecastResponse

    @GET(Url.WEATHER_PATH)
    suspend fun getWeatherByCityId(
        @Query("id") cityId: Long,
        @Query("appid") apiKey: String = Url.API_KEY
    ): RemoteForecastResponse
}