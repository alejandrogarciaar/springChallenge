package com.jgarcia.springchallenge.data.mappers

import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.remoteData.model.RemoteForecastResponse
import javax.inject.Inject

class ForecastMapper @Inject constructor() {
    operator fun invoke(remoteForecastResponse: RemoteForecastResponse): Forecast {
        return Forecast(
            location = Forecast.Location(
                latitude = remoteForecastResponse.coordinates.latitude,
                longitude = remoteForecastResponse.coordinates.longitude
            ),
            weather = Forecast.Weather(
                id = remoteForecastResponse.weather.id,
                main = remoteForecastResponse.weather.main,
                description = remoteForecastResponse.weather.description
            ),
            main = Forecast.Main(
                temp = remoteForecastResponse.main.temp,
                feelsLike = remoteForecastResponse.main.feelsLike,
                tempMin = remoteForecastResponse.main.tempMin,
                tempMax = remoteForecastResponse.main.tempMax,
                pressure = remoteForecastResponse.main.pressure,
                humidity = remoteForecastResponse.main.humidity
            ),
            wind = Forecast.Wind(
                speed = remoteForecastResponse.wind.speed,
                deg = remoteForecastResponse.wind.deg
            ),
            id = remoteForecastResponse.id,
            name = remoteForecastResponse.name
        )
    }
}