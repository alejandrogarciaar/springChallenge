package com.jgarcia.springchallenge.usecases.localLocations

import com.jgarcia.springchallenge.data.repository.LocalForecastRepository
import com.jgarcia.springchallenge.domain.Forecast
import javax.inject.Inject

class SaveLocation @Inject constructor(private val localForecastRepository: LocalForecastRepository) {
    suspend operator fun invoke(location: Forecast.Location) {
        localForecastRepository.insertLocation(location)
    }
}