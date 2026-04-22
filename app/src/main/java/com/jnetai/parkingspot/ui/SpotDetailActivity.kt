package com.jnetai.parkingspot.ui

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jnetai.parkingspot.ParkingApp
import com.jnetai.parkingspot.databinding.ActivitySpotDetailBinding
import kotlinx.coroutines.launch

class SpotDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySpotDetailBinding
    private val app get() = application as ParkingApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpotDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val spotId = intent.getLongExtra("spot_id", -1)
        lifecycleScope.launch {
            val spot = app.database.dao().getSpot(spotId) ?: return@launch
            supportActionBar?.title = spot.name
            binding.nameText.text = spot.name
            binding.addressText.text = spot.address.ifEmpty { "No address" }
            binding.rateText.text = if (spot.hourlyRate > 0) "Rate: £${spot.hourlyRate}/hr" else "Free parking"
            binding.maxHoursText.text = if (spot.maxHours > 0) "Max: ${spot.maxHours}hrs" else "No time limit"
            binding.notesText.text = spot.notes.ifEmpty { "No notes" }
            binding.favText.text = if (spot.isFavourite) "⭐ Favourite" else ""

            binding.deleteButton.setOnClickListener {
                AlertDialog.Builder(this@SpotDetailActivity)
                    .setTitle("Delete ${spot.name}?")
                    .setPositiveButton("Delete") { _, _ ->
                        lifecycleScope.launch { app.database.dao().deleteSpot(spot); finish() }
                    }
                    .setNegativeButton("Cancel", null)
                    .show()
            }

            binding.toggleFavButton.setOnClickListener {
                lifecycleScope.launch {
                    app.database.dao().updateSpot(spot.copy(isFavourite = !spot.isFavourite))
                    recreate()
                }
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}