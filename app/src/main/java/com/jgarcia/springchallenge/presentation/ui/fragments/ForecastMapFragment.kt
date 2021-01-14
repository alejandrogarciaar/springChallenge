package com.jgarcia.springchallenge.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.jgarcia.springchallenge.R
import com.jgarcia.springchallenge.databinding.FragmentMapBinding
import com.jgarcia.springchallenge.domain.Forecast
import com.jgarcia.springchallenge.domain.util.Result
import com.jgarcia.springchallenge.presentation.adapters.LocationAdapter
import com.jgarcia.springchallenge.presentation.util.hide
import com.jgarcia.springchallenge.presentation.util.invisible
import com.jgarcia.springchallenge.presentation.util.show
import com.jgarcia.springchallenge.presentation.viewModel.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForecastMapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private lateinit var locationAdapter: LocationAdapter
    private lateinit var fragmentMapBinding: FragmentMapBinding
    private val forecastViewModel: ForecastViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentMapBinding = FragmentMapBinding.inflate(inflater, container, false)
        addSubscriptions()
        buildLocationsAdapter()
        setLocationsRecyclerView()
        setBottomSheetBehavior()
        renderMap()
        return fragmentMapBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forecastViewModel.getStoredLocations()
    }

    private fun addSubscriptions() {

        forecastViewModel.observeStoredLocations().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Loading -> {
                    fragmentMapBinding.containerBottomSheet.pbLoading.show()
                    fragmentMapBinding.containerBottomSheet.tvEmptyStoredLocations.hide()
                    fragmentMapBinding.containerBottomSheet.tvSwipeUp.invisible()
                }
                is Result.Error -> {
                    fragmentMapBinding.containerBottomSheet.pbLoading.hide()
                    fragmentMapBinding.containerBottomSheet.tvEmptyStoredLocations.hide()
                    fragmentMapBinding.containerBottomSheet.tvSwipeUp.invisible()
                }
                is Result.Success -> {
                    fragmentMapBinding.containerBottomSheet.pbLoading.hide()
                    locationAdapter.updateItems(result.data)
                    if (result.data.isEmpty()) {
                        fragmentMapBinding.containerBottomSheet.tvEmptyStoredLocations.show()
                    } else {
                        fragmentMapBinding.containerBottomSheet.tvSwipeUp.show()
                    }
                }
            }
        })
    }

    private fun buildLocationsAdapter() {
        locationAdapter = LocationAdapter(
            context = requireContext(),
            onRemoveClicked = { forecastViewModel.deleteLocation(it) },
            onLocationClicked = { goToForecastDetail(it) }
        )
    }

    private fun setLocationsRecyclerView() {
        with(fragmentMapBinding.containerBottomSheet.rvLocations) {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = locationAdapter
        }
    }

    private fun setBottomSheetBehavior() {
        bottomSheetBehavior = BottomSheetBehavior.from(fragmentMapBinding.containerBottomSheet.root)
    }

    private fun renderMap() {
        val mapFragment = childFragmentManager.findFragmentById(R.id.googleMap) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val defaultLatLng = LatLng(4.5981, -74.0758)
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLatLng))
        // set long click listener
        googleMap.setOnMapLongClickListener {
            googleMap.addMarker(MarkerOptions().position(it))
            forecastViewModel.saveLocation(Forecast.Location(longitude = it.longitude, latitude = it.latitude))
        }
    }

    private fun goToForecastDetail(location: Forecast.Location) {
        val forecastDetailAction = ForecastMapFragmentDirections.actionMapFragmentToForecastDetailFragment(location)
        findNavController().navigate(forecastDetailAction)
    }
}