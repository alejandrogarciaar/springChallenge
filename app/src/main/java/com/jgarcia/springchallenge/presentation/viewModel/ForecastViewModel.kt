package com.jgarcia.springchallenge.presentation.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.usecases.localLocations.DeleteLocation
import com.jgarcia.springchallenge.usecases.localLocations.GetStoredLocations
import com.jgarcia.springchallenge.usecases.localLocations.SaveLocation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ForecastViewModel @ViewModelInject constructor(
    private val getStoredLocations: GetStoredLocations,
    private val saveLocation: SaveLocation,
    private val deleteLocation: DeleteLocation
) : ViewModel(), CoroutineScope {

    // Provided for testing purpose
    override val coroutineContext: CoroutineContext = Dispatchers.IO

    private val storedLocations: MutableLiveData<Result<List<Forecast.Location>>> = MutableLiveData()

    fun observeStoredLocations() = storedLocations

    fun getStoredLocations() {
        viewModelScope.launch(coroutineContext) {
            storedLocations.postValue(Result.Loading)
            storedLocations.postValue(getStoredLocations.invoke())
        }
    }

    fun saveLocation(location: Forecast.Location) {
        viewModelScope.launch(coroutineContext) {
            storedLocations.postValue(Result.Loading)
            saveLocation.invoke(location)
            storedLocations.postValue(getStoredLocations.invoke())
        }
    }

    fun deleteLocation(location: Forecast.Location) {
        viewModelScope.launch(coroutineContext) {
            storedLocations.postValue(Result.Loading)
            deleteLocation.invoke(location)
            storedLocations.postValue(getStoredLocations.invoke())
        }
    }
}