package com.jgarcia.springchallenge.presentation.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.usecases.forecast.GetForecastByCoordinates
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ForecastDetailViewModel @ViewModelInject constructor(
    private val getForecastByCoordinates: GetForecastByCoordinates
) : ViewModel(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val currentForecast: MutableLiveData<Result<Forecast>> = MutableLiveData()

    fun observerCurrentForecast() = currentForecast

    fun fetchForecastByCoordinates(location: Forecast.Location) {
        viewModelScope.launch(coroutineContext) {
            currentForecast.postValue(Result.Loading)
            currentForecast.postValue(getForecastByCoordinates.invoke(longitude = location.longitude, latitude = location.latitude))
        }
    }
}