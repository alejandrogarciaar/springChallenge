package com.jgarcia.springchallenge.mocks

import com.jgarcia.springchallenge.remoteData.model.RemoteForecastResponse

val mockRemoteForecastResponse = RemoteForecastResponse(
    coordinates = RemoteForecastResponse.RemoteCoordinates(longitude = 10.0, latitude = 10.0),
    weather = listOf(
        RemoteForecastResponse.RemoteWeather(
            id = 100,
            main = "mockMain",
            description = "mockDescription",
            icon = "mockIcon"
        )
    ),
    main = RemoteForecastResponse.RemoteMain(
        temp = 10.0,
        feelsLike = 10.0,
        tempMin = 10.0,
        tempMax = 10.0,
        pressure = 10.0,
        humidity = 10
    ),
    wind = RemoteForecastResponse.RemoteWind(
        speed = 10.0,
        deg = 10
    ),
    id = 20,
    name = "mockForecast"
)