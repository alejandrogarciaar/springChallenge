package com.jgarcia.springchallenge.mocks

import com.jgarcia.springchallenge.domain.Forecast

val mockForecastLocation = Forecast.Location(
    longitude = 10.0,
    latitude = 10.0
)

val mockForecastLocationList = listOf(
    Forecast.Location(
        longitude = 10.0,
        latitude = 10.0
    ), Forecast.Location(
        longitude = 20.0,
        latitude = 20.0
    ), Forecast.Location(
        longitude = 30.0,
        latitude = 30.0
    )
)