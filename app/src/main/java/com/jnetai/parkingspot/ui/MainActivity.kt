package com.jnetai.parkingspot.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jnetai.parkingspot.ParkingApp
import com.jnetai.parkingspot.R
import com.jnetai.parkingspot.databinding.ActivityMainBinding
import com.jnetai.parkingspot.model.ParkingSpot
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val app get() = application as ParkingApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Parking Spot Finder"

        val adapter = SpotAdapter { spot -> startActivity(Intent(this, SpotDetailActivity::class.java).putExtra("spot_id", spot.id)) }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        refreshList()
        binding.fab.setOnClickListener { startActivity(Intent(this, AddSpotActivity::class.java)) }
    }

    override fun onResume() { super.onResume(); refreshList() }

    private fun refreshList() {
        lifecycleScope.launch {
            val spots = app.database.dao().getAllSpots()
            (binding.recyclerView.adapter as SpotAdapter).items = spots
            (binding.recyclerView.adapter as SpotAdapter).notifyDataSetChanged()
            binding.emptyView.visibility = if (spots.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean { menuInflater.inflate(R.menu.menu_main, menu); return true }
    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_sessions -> { startActivity(Intent(this, SessionsActivity::class.java)); true }
        R.id.action_about -> { startActivity(Intent(this, AboutActivity::class.java)); true }
        else -> super.onOptionsItemSelected(item)
    }
}