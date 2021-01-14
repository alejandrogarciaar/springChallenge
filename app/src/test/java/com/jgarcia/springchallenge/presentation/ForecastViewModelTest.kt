package com.jgarcia.springchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.ErrorType
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.mocks.mockForecastLocation
import com.jgarcia.springchallenge.mocks.mockForecastLocationList
import com.jgarcia.springchallenge.presentation.viewModel.ForecastViewModel
import com.jgarcia.springchallenge.usecases.localLocations.DeleteLocation
import com.jgarcia.springchallenge.usecases.localLocations.GetStoredLocations
import com.jgarcia.springchallenge.usecases.localLocations.SaveLocation
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ForecastViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var getStoredLocations: GetStoredLocations

    @Mock
    private lateinit var saveLocation: SaveLocation

    @Mock
    private lateinit var deleteLocation: DeleteLocation

    @Mock
    private lateinit var storedLocationsObserver: Observer<Result<List<Forecast.Location>>>

    private lateinit var forecastViewModel: ForecastViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastViewModel = ForecastViewModel(getStoredLocations, saveLocation, deleteLocation).apply {
            coroutineContext = testDispatcher
            observeStoredLocations().observeForever(storedLocationsObserver)
        }
    }

    @Test
    fun `verify if observeStoredLocations() method is notifying ResultSuccess`() = runBlockingTest {
        // Given
        whenever(getStoredLocations.invoke()).thenReturn(Result.Success(mockForecastLocationList))

        // When
        forecastViewModel.getStoredLocations()

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Success(mockForecastLocationList))
    }

    @Test
    fun `verify if observeStoredLocations() method is notifying UnknownError`() = runBlockingTest {
        // Given
        whenever(getStoredLocations.invoke()).thenReturn(Result.Error(ErrorType.UnknownError))

        // When
        forecastViewModel.getStoredLocations()

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Error(ErrorType.UnknownError))
    }

    @Test
    fun `verify if observeStoredLocations() method is notifying NetworkError`() = runBlockingTest {
        // Given
        whenever(getStoredLocations.invoke()).thenReturn(Result.Error(ErrorType.NetworkError))

        // When
        forecastViewModel.getStoredLocations()

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Error(ErrorType.NetworkError))
    }

    @Test
    fun `verify if observeStoredLocations() method is notifying TimeoutException`() = runBlockingTest {
        // Given
        whenever(getStoredLocations.invoke()).thenReturn(Result.Error(ErrorType.TimeoutException))

        // When
        forecastViewModel.getStoredLocations()

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Error(ErrorType.TimeoutException))
    }

    @Test
    fun `verify if saveLocation() method is not changing the response of getStoredLocations()`() = runBlockingTest {
        // Given
        whenever(getStoredLocations.invoke()).thenReturn(Result.Success(mockForecastLocationList))

        // When
        forecastViewModel.saveLocation(mockForecastLocation)

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Success(mockForecastLocationList))
    }

    @Test
    fun `verify if deleteLocation() method is removing the selected location`() = runBlockingTest {
        // Given
        val mockForecastLocationListWithoutElement = mockForecastLocationList.toMutableList()
        mockForecastLocationListWithoutElement.remove(mockForecastLocation)
        whenever(getStoredLocations.invoke()).thenReturn(Result.Success(mockForecastLocationListWithoutElement))

        // When
        forecastViewModel.deleteLocation(mockForecastLocation)

        // Then
        verify(storedLocationsObserver).onChanged(Result.Loading)
        verify(storedLocationsObserver).onChanged(Result.Success(mockForecastLocationListWithoutElement))
    }

}