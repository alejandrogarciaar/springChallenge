package com.jgarcia.springchallenge.data.repository.impl

import com.jgarcia.springchallenge.data.repository.LocalForecastRepository
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.ErrorType
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.framework.dao.LocationDAO
import java.lang.Exception
import javax.inject.Inject

class LocalForecastRepositoryImpl @Inject constructor(
    private val locationDAO: LocationDAO
) : LocalForecastRepository {

    override suspend fun getStoredLocations(): Result<List<Forecast.Location>> {
        return try {
            Result.Success(locationDAO.getAll())
        } catch (exception: Exception) {
            Result.Error(ErrorType.UnknownError)
        }
    }

    override suspend fun insertLocation(location: Forecast.Location) {
        locationDAO.insert(location)
    }

    override suspend fun removeLocation(location: Forecast.Location) {
        locationDAO.delete(location)
    }
}