package com.jgarcia.springchallenge.data.dataSource

import com.jgarcia.springchallenge.domain.Forecast

interface ForecastDataSource {
    suspend fun getForecastByCoordinates(latitude: Double, longitude: Double): Forecast
}