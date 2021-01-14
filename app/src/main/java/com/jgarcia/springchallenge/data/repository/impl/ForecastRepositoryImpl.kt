package com.jgarcia.springchallenge.data.repository.impl

import com.jgarcia.springchallenge.data.dataSource.ForecastDataSource
import com.jgarcia.springchallenge.data.repository.ForecastRepository
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.ErrorType
import com.jgarcia.springchallenge.domain.util.Result
import retrofit2.HttpException
import java.lang.Exception
import java.net.SocketTimeoutException
import javax.inject.Inject

class ForecastRepositoryImpl @Inject constructor(
    private val forecastDataSource: ForecastDataSource
) : ForecastRepository {
    override suspend fun getForecastByCoordinates(latitude: Double, longitude: Double): Result<Forecast> {
        return try {
            Result.Success(forecastDataSource.getForecastByCoordinates(latitude, longitude))
        } catch (exception: HttpException) {
            Result.Error(ErrorType.NetworkError)
        } catch (exception: SocketTimeoutException) {
            Result.Error(ErrorType.TimeoutException)
        } catch (exception: Exception) {
            Result.Error(ErrorType.UnknownError)
        }
    }
}