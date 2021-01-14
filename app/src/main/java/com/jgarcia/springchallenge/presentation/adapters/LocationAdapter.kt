package com.jgarcia.springchallenge.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgarcia.springchallenge.R
import com.jgarcia.springchallenge.databinding.LayoutLocationItemBinding
import com.jgarcia.springchallenge.domain.Forecast

class LocationAdapter(
    private val context: Context,
    private val onLocationClicked: (location: Forecast.Location) -> Unit,
    private val onRemoveClicked: (location: Forecast.Location) -> Unit
) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {

    private var currentLocations: MutableList<Forecast.Location> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val layoutLocationItemBinding = LayoutLocationItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LocationViewHolder(layoutLocationItemBinding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        holder.bind(currentLocations[position])
    }

    override fun getItemCount() = currentLocations.size

    fun updateItems(newLocations: List<Forecast.Location>) {
        this.currentLocations.clear()
        this.currentLocations.addAll(newLocations)
        this.notifyDataSetChanged()
    }

    inner class LocationViewHolder(
        private val layoutLocationItemBinding: LayoutLocationItemBinding
    ) : RecyclerView.ViewHolder(layoutLocationItemBinding.root) {
        fun bind(location: Forecast.Location) {
            layoutLocationItemBinding.tvLatitude.text = context.getString(R.string.stored_location_latitude, location.latitude)
            layoutLocationItemBinding.tvLongitude.text = context.getString(R.string.stored_location_longitude, location.longitude)
            layoutLocationItemBinding.ivRemove.setOnClickListener { onRemoveClicked(location) }
            layoutLocationItemBinding.containerLocation.setOnClickListener { onLocationClicked(location) }
        }
    }
}