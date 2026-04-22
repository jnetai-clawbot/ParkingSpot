package com.jnetai.parkingspot.ui

import android.os.Bundle
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jnetai.parkingspot.ParkingApp
import com.jnetai.parkingspot.databinding.ActivityAddSpotBinding
import com.jnetai.parkingspot.model.ParkingSpot
import kotlinx.coroutines.launch

class AddSpotActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddSpotBinding
    private val app get() = application as ParkingApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddSpotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Add Parking Spot"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.saveButton.setOnClickListener {
            val name = binding.nameEdit.text?.toString()?.trim() ?: ""
            if (name.isBlank()) { Toast.makeText(this, "Name required", Toast.LENGTH_SHORT).show(); return@setOnClickListener }
            lifecycleScope.launch {
                app.database.dao().insertSpot(ParkingSpot(
                    name = name,
                    address = binding.addressEdit.text?.toString()?.trim() ?: "",
                    hourlyRate = binding.rateEdit.text?.toString()?.trim()?.toDoubleOrNull() ?: 0.0,
                    maxHours = binding.maxHoursEdit.text?.toString()?.trim()?.toDoubleOrNull() ?: 0.0,
                    notes = binding.notesEdit.text?.toString()?.trim() ?: "",
                    isFavourite = binding.favouriteCheck.isChecked
                ))
                finish()
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}