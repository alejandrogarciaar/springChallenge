package com.jgarcia.springchallenge.data.repository

import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result

interface LocalForecastRepository {
    suspend fun getStoredLocations(): Result<List<Forecast.Location>>
    suspend fun insertLocation(location: Forecast.Location)
    suspend fun removeLocation(location: Forecast.Location)
}