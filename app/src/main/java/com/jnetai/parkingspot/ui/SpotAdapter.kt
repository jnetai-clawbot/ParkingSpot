package com.jnetai.parkingspot.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jnetai.parkingspot.databinding.ItemSpotBinding
import com.jnetai.parkingspot.model.ParkingSpot

class SpotAdapter(private val onClick: (ParkingSpot) -> Unit) : RecyclerView.Adapter<SpotAdapter.VH>() {
    var items: List<ParkingSpot> = emptyList()
    inner class VH(val binding: ItemSpotBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemSpotBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val s = items[pos]
        h.binding.nameText.text = if (s.isFavourite) "⭐ ${s.name}" else s.name
        h.binding.addressText.text = s.address.ifEmpty { "No address" }
        h.binding.rateText.text = if (s.hourlyRate > 0) "£${s.hourlyRate}/hr" else "Free"
        h.binding.root.setOnClickListener { onClick(s) }
    }
}