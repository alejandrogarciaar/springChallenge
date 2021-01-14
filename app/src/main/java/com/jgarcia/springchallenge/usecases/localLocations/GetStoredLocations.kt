package com.jgarcia.springchallenge.usecases.localLocations

import com.jgarcia.springchallenge.data.repository.LocalForecastRepository
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import javax.inject.Inject

class GetStoredLocations @Inject constructor(private val localForecastRepository: LocalForecastRepository) {
    suspend operator fun invoke(): Result<List<Forecast.Location>> {
        return localForecastRepository.getStoredLocations()
    }
}