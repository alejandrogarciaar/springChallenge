package com.jgarcia.springchallenge.mocks

import com.jgarcia.springchallenge.domain.Forecast

val mockForecast = Forecast(
    location = Forecast.Location(
        id = null,
        longitude = 10.0,
        latitude = 10.0
    ),
    weather = listOf(
        Forecast.Weather(
            id = 100, main = "mockMain",
            description = "mockDescription"
        )
    ),
    main = Forecast.Main(
        temp = 10.0,
        feelsLike = 10.0,
        tempMin = 10.0,
        tempMax = 10.0,
        pressure = 10.0,
        humidity = 10
    ),
    wind = Forecast.Wind(
        speed = 10.0,
        deg = 10
    ),
    id = 20,
    name = "mockForecast"
)