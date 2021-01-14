package com.jgarcia.springchallenge.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.jgarcia.springchallenge.R
import com.jgarcia.springchallenge.databinding.ActivityHomeBinding
import com.jgarcia.springchallenge.presentation.viewModel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var currentGoogleMap: GoogleMap
    private lateinit var activityHomeBinding: ActivityHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(activityHomeBinding.root)
        renderMap()
    }

    private fun renderMap() {
        val mapFragment = supportFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.currentGoogleMap = googleMap
        val defaultLatLng = LatLng(4.5981, -74.0758)
        this.currentGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng))
        this.currentGoogleMap.uiSettings.isZoomControlsEnabled = true

        this.currentGoogleMap.setOnMapClickListener {
            this.currentGoogleMap.addMarker(MarkerOptions().position(it))
        }
    }
}