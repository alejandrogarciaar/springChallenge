package com.jgarcia.springchallenge.remoteData.api

import com.jgarcia.springchallenge.remoteData.model.RemoteForecastResponse
import com.jgarcia.springchallenge.remoteData.util.Url
import retrofit2.http.GET
import retrofit2.http.Query

interface ForecastAPI {

    @GET(Url.WEATHER_PATH)
    suspend fun getForecastByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String = Url.API_KEY
    ): RemoteForecastResponse
}