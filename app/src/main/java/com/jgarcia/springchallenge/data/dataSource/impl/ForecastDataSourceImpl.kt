package com.jgarcia.springchallenge.data.dataSource.impl

import com.jgarcia.springchallenge.data.dataSource.ForecastDataSource
import com.jgarcia.springchallenge.data.mappers.ForecastMapper
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.remoteData.api.ForecastAPI
import javax.inject.Inject

class ForecastDataSourceImpl @Inject constructor(
    private val forecastMapper: ForecastMapper,
    private val forecastAPI: ForecastAPI
) : ForecastDataSource {

    override suspend fun getForecastByCoordinates(latitude: Double, longitude: Double): Forecast {
        return forecastMapper.invoke(forecastAPI.getForecastByCoordinates(latitude, longitude))
    }
}