package com.jnetai.parkingspot.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.jnetai.parkingspot.ParkingApp
import com.jnetai.parkingspot.databinding.ActivitySessionsBinding
import com.jnetai.parkingspot.databinding.ItemSessionBinding
import com.jnetai.parkingspot.model.ParkingSession
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class SessionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySessionsBinding
    private val app get() = application as ParkingApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySessionsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Parking History"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = SessionAdapter()
        binding.sessionRecycler.adapter = adapter
        lifecycleScope.launch { adapter.items = app.database.dao().getAllSessions(); adapter.notifyDataSetChanged() }
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}

class SessionAdapter : RecyclerView.Adapter<SessionAdapter.VH>() {
    var items: List<ParkingSession> = emptyList()
    private val df = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
    inner class VH(val binding: ItemSessionBinding) : RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(ItemSessionBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    override fun getItemCount() = items.size
    override fun onBindViewHolder(h: VH, pos: Int) {
        val s = items[pos]
        h.binding.spotIdText.text = "Spot #${s.spotId}"
        h.binding.startTimeText.text = "Start: ${df.format(Date(s.startTime))}"
        h.binding.endTimeText.text = if (s.endTime > 0) "End: ${df.format(Date(s.endTime))}" else "Active"
        h.binding.costText.text = if (s.cost > 0) "£${String.format("%.2f", s.cost)}" else ""
    }
}