package com.jgarcia.springchallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.jgarcia.springchallenge.R
import com.jgarcia.springchallenge.databinding.FragmentForecastDetailBinding
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.presentation.util.hide
import com.jgarcia.springchallenge.presentation.util.show
import com.jgarcia.springchallenge.presentation.viewModel.ForecastDetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastDetailFragment : Fragment() {

    private val forecastDetailViewModel: ForecastDetailViewModel by activityViewModels()
    private val args: ForecastDetailFragmentArgs by navArgs()
    private lateinit var fragmentForecastDetailBinding: FragmentForecastDetailBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentForecastDetailBinding = FragmentForecastDetailBinding.inflate(inflater, container, false)
        addSubscriptions()
        return fragmentForecastDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastDetailViewModel.fetchForecastByCoordinates(args.locationClicked)
    }

    private fun addSubscriptions() {

        forecastDetailViewModel.observerCurrentForecast().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    fragmentForecastDetailBinding.pgLoading.show()
                }
                is Result.Error -> {
                    fragmentForecastDetailBinding.pgLoading.hide()
                    fragmentForecastDetailBinding.tvError.show()
                }
                is Result.Success -> {
                    fragmentForecastDetailBinding.pgLoading.hide()
                    fragmentForecastDetailBinding.defaultGroup.show()
                    populateUI(result.data)
                }
            }
        })
    }

    private fun populateUI(forecast: Forecast) {
        // city
        fragmentForecastDetailBinding.tvCityName.text = getString(R.string.forecast_city_name, forecast.name)
        val availableWeather = forecast.weather.firstOrNull()
        availableWeather?.let {
            fragmentForecastDetailBinding.tvMain.text = getString(R.string.forecast_main, it.main)
            fragmentForecastDetailBinding.tvMainDescription.text = getString(R.string.forecast_main_description, it.description)
        }
        fragmentForecastDetailBinding.tvCurrentTemp.text = getString(R.string.forecast_current_temp, forecast.main.temp)
        fragmentForecastDetailBinding.tvMinTemp.text = getString(R.string.forecast_current_min_temp, forecast.main.tempMin)
        fragmentForecastDetailBinding.tvMaxTemp.text = getString(R.string.forecast_current_max_temp, forecast.main.tempMax)
        fragmentForecastDetailBinding.tvWindSpeed.text = getString(R.string.forecast_current_wind_speed, forecast.wind.speed)
        fragmentForecastDetailBinding.tvHumidity.text = getString(R.string.forecast_current_humidity, forecast.main.humidity)
    }
}