package com.jgarcia.springchallenge.presentation.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jgarcia.springchallenge.usecases.GetForecastByCoordinates
import kotlinx.coroutines.launch

class HomeViewModel @ViewModelInject constructor(
    private val getForecastByCoordinates: GetForecastByCoordinates
) : ViewModel() {

    fun getForecastByCoordinates() {
        viewModelScope.launch {

        }
    }
}