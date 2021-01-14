package com.jgarcia.springchallenge.usecases

import com.jgarcia.springchallenge.data.repository.ForecastRepository
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import javax.inject.Inject

class GetForecastByCoordinates @Inject constructor(private val forecastRepository: ForecastRepository) {
    suspend operator fun invoke(latitude: Double, longitude: Double): Result<Forecast> {
        return forecastRepository.getForecastByCoordinates(latitude, longitude)
    }
}