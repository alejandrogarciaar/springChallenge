package com.jgarcia.springchallenge.data.mappers

import com.jgarcia.springchallenge.mocks.mockForecast
import com.jgarcia.springchallenge.mocks.mockRemoteForecastResponse
import org.junit.Assert.assertEquals
import org.junit.Test

class ForecastMapperTest {

    @Test
    fun `verify if ForecastMapper is returning as expected`() {
        // Given
        val forecastMapper = ForecastMapper()

        // When
        val forecast = forecastMapper.invoke(mockRemoteForecastResponse)

        // Then
        assertEquals(mockForecast, forecast)
    }
}