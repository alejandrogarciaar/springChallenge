package com.jgarcia.springchallenge.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jgarcia.springchallenge.R
import com.jgarcia.springchallenge.databinding.ActivityHomeBinding
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.presentation.util.hide
import com.jgarcia.springchallenge.presentation.util.show
import com.jgarcia.springchallenge.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var currentGoogleMap: GoogleMap
    private lateinit var activityHomeBinding: ActivityHomeBinding
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        setBottomSheet()
        addSubscriptions()
        renderMap()
        getStoredLocations()
    }

    private fun setBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(activityHomeBinding.containerBottomSheet.root)
    }

    private fun addSubscriptions() {

        // observer to stored locations
        homeViewModel.observeStoredLocations().observe(this, { result ->
            when (result) {
                is Result.Loading -> {
                    activityHomeBinding.containerBottomSheet.pbLoading.show()
                    activityHomeBinding.containerBottomSheet.tvEmptyStoredLocations.hide()
                    activityHomeBinding.containerBottomSheet.tvSwipeUp.hide()
                }
                is Result.Error -> {
                    activityHomeBinding.containerBottomSheet.pbLoading.hide()
                    activityHomeBinding.containerBottomSheet.tvSwipeUp.hide()
                    activityHomeBinding.containerBottomSheet.tvEmptyStoredLocations.hide()
                }
                is Result.Success -> {
                    activityHomeBinding.containerBottomSheet.pbLoading.hide()
                    if (result.data.isEmpty()) {
                        activityHomeBinding.containerBottomSheet.tvEmptyStoredLocations.show()
                    } else {
                        activityHomeBinding.containerBottomSheet.tvSwipeUp.show()
                    }
                }
            }
        })
    }

    private fun getStoredLocations() {
        homeViewModel.getStoredLocations()
    }

    private fun renderMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.currentGoogleMap = googleMap
        val defaultLatLng = LatLng(4.5981, -74.0758)
        this.currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng))

        this.currentGoogleMap.setOnMapLongClickListener {
            this.currentGoogleMap.addMarker(MarkerOptions().position(it))
            homeViewModel.saveLocation(Forecast.Location(longitude = it.longitude, latitude = it.latitude))
        }
    }
}