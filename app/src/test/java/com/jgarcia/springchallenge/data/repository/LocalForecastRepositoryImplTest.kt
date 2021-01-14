package com.jgarcia.springchallenge.data.repository

import com.jgarcia.springchallenge.data.repository.impl.LocalForecastRepositoryImpl
import com.jgarcia.springchallenge.framework.dao.LocationDAO
import com.jgarcia.springchallenge.mocks.mockForecastLocation
import com.jgarcia.springchallenge.mocks.mockForecastLocationList
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class LocalForecastRepositoryImplTest {

    private lateinit var localForecastRepositoryImpl: LocalForecastRepositoryImpl

    @Mock
    private lateinit var locationDAO: LocationDAO

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        localForecastRepositoryImpl = LocalForecastRepositoryImpl(locationDAO)
    }

    @Test
    fun `verify if getAll() was invoked correctly`() = runBlocking<Unit>(Dispatchers.IO) {
        // Given
        whenever(locationDAO.getAll()).thenReturn(mockForecastLocationList)

        // When
        localForecastRepositoryImpl.getStoredLocations()

        // Then
        verify(locationDAO, times(1)).getAll()
    }

    @Test
    fun `verify if insertLocation was invoked correctly`() = runBlocking(Dispatchers.IO) {

        // When
        localForecastRepositoryImpl.insertLocation(mockForecastLocation)

        // Then
        verify(locationDAO, times(1)).insert(mockForecastLocation)
    }
}