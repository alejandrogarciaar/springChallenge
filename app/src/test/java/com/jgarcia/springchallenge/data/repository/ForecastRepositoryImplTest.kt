package com.jgarcia.springchallenge.data.repository

import com.jgarcia.springchallenge.data.dataSource.impl.ForecastDataSourceImpl
import com.jgarcia.springchallenge.data.repository.impl.ForecastRepositoryImpl
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.mocks.mockForecast
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ForecastRepositoryImplTest {

    private lateinit var forecastRepositoryImpl: ForecastRepositoryImpl

    @Mock
    private lateinit var forecastDataSource: ForecastDataSourceImpl

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastRepositoryImpl = ForecastRepositoryImpl(forecastDataSource)
    }

    @Test
    fun `verify if invoke() of forecastDataSource is called when forecastRepositoryImpl was invoked`() = runBlocking<Unit>(Dispatchers.IO) {
        // When
        forecastRepositoryImpl.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)

        // Then
        verify(forecastDataSource, times(1)).getForecastByCoordinates(latitude = 10.0, longitude = 10.0)
    }

    @Test
    fun `verify if mock result is same as returned`() = runBlocking(Dispatchers.IO) {
        // When
        whenever(forecastDataSource.getForecastByCoordinates(longitude = 10.0, latitude = 10.0)).thenReturn(mockForecast)

        // When
        val result = forecastRepositoryImpl.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)

        // Then
        assertEquals(Result.Success(mockForecast), result)
    }
}