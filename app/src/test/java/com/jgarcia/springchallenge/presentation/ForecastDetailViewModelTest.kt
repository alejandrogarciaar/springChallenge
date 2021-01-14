package com.jgarcia.springchallenge.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.ErrorType
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.mocks.mockForecast
import com.jgarcia.springchallenge.mocks.mockForecastLocation
import com.jgarcia.springchallenge.presentation.viewModel.ForecastDetailViewModel
import com.jgarcia.springchallenge.usecases.forecast.GetForecastByCoordinates
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
class ForecastDetailViewModelTest {

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var getForecastByCoordinates: GetForecastByCoordinates

    @Mock
    private lateinit var currentForecastObserver: Observer<Result<Forecast>>

    private lateinit var forecastDetailViewModel: ForecastDetailViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        forecastDetailViewModel = ForecastDetailViewModel(getForecastByCoordinates).apply {
            coroutineContext = testDispatcher
            observerCurrentForecast().observeForever(currentForecastObserver)
        }
    }

    @Test
    fun `verify if observerCurrentForecast() method is notifying ResultSuccess`() = runBlockingTest {
        // Given
        whenever(getForecastByCoordinates.invoke(latitude = 10.0, longitude = 10.0)).thenReturn(Result.Success(mockForecast))

        // When
        forecastDetailViewModel.fetchForecastByCoordinates(location = mockForecastLocation)

        // Then
        verify(currentForecastObserver).onChanged(Result.Loading)
        verify(currentForecastObserver).onChanged(Result.Success(mockForecast))
    }

    @Test
    fun `verify if observerCurrentForecast() method is notifying UnknownError`() = runBlockingTest {
        // Given
        whenever(getForecastByCoordinates.invoke(latitude = 10.0, longitude = 10.0)).thenReturn(Result.Error(ErrorType.UnknownError))

        // When
        forecastDetailViewModel.fetchForecastByCoordinates(location = mockForecastLocation)

        // Then
        verify(currentForecastObserver).onChanged(Result.Loading)
        verify(currentForecastObserver).onChanged(Result.Error(ErrorType.UnknownError))
    }

    @Test
    fun `verify if observerCurrentForecast() method is notifying NetworkError`() = runBlockingTest {
        // Given
        whenever(getForecastByCoordinates.invoke(latitude = 10.0, longitude = 10.0)).thenReturn(Result.Error(ErrorType.NetworkError))

        // When
        forecastDetailViewModel.fetchForecastByCoordinates(location = mockForecastLocation)

        // Then
        verify(currentForecastObserver).onChanged(Result.Loading)
        verify(currentForecastObserver).onChanged(Result.Error(ErrorType.NetworkError))
    }

    @Test
    fun `verify if observerCurrentForecast() method is notifying TimeoutException`() = runBlockingTest {
        // Given
        whenever(getForecastByCoordinates.invoke(latitude = 10.0, longitude = 10.0)).thenReturn(Result.Error(ErrorType.TimeoutException))

        // When
        forecastDetailViewModel.fetchForecastByCoordinates(location = mockForecastLocation)

        // Then
        verify(currentForecastObserver).onChanged(Result.Loading)
        verify(currentForecastObserver).onChanged(Result.Error(ErrorType.TimeoutException))
    }

}