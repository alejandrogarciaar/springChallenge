package com.jgarcia.springchallenge.data.repository

import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result

interface ForecastRepository {
    suspend fun getForecastByCoordinates(latitude: Double, longitude: Double): Result<Forecast>
}