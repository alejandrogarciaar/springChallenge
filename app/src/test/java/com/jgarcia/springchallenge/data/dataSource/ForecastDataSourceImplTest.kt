package com.jgarcia.springchallenge.data.dataSource

import com.jgarcia.springchallenge.data.dataSource.impl.ForecastDataSourceImpl
import com.jgarcia.springchallenge.data.mappers.ForecastMapper
import com.jgarcia.springchallenge.mocks.mockForecast
import com.jgarcia.springchallenge.mocks.mockRemoteForecastResponse
import com.jgarcia.springchallenge.remoteData.api.ForecastAPI
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ForecastDataSourceImplTest {

    private lateinit var forecastDataSourceImpl: ForecastDataSourceImpl

    @Mock
    private lateinit var forecastMapper: ForecastMapper

    @Mock
    private lateinit var forecastAPI: ForecastAPI

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastDataSourceImpl = ForecastDataSourceImpl(forecastMapper, forecastAPI)
    }

    @Test
    fun `verify if forecastMapper was invoked when getForecastByCoordinates() method is called`() = runBlocking<Unit>(Dispatchers.IO) {
        //Given
        whenever(forecastAPI.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)).thenReturn(mockRemoteForecastResponse)

        // When
        forecastDataSourceImpl.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)

        // Then
        verify(forecastAPI, times(1)).getForecastByCoordinates(latitude = 10.0, longitude = 10.0)
        verify(forecastMapper, times(1)).invoke(any())
    }

    @Test
    fun `verify if result of getForecastByCoordinates() is the same as expected`() = runBlocking(Dispatchers.IO) {
        //Given
        whenever(forecastAPI.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)).thenReturn(mockRemoteForecastResponse)
        whenever(forecastMapper.invoke(mockRemoteForecastResponse)).thenReturn(mockForecast)
        //When
        val forecastFromDataSource = forecastDataSourceImpl.getForecastByCoordinates(latitude = 10.0, longitude = 10.0)
        //Then
        assertEquals(ForecastMapper().invoke(mockRemoteForecastResponse), forecastFromDataSource)
    }
}