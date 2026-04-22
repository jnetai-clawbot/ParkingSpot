package com.jnetai.parkingspot.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jnetai.parkingspot.BuildConfig
import com.jnetai.parkingspot.databinding.ActivityAboutBinding
import org.json.JSONObject
import java.net.URL

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "About"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.versionText.text = "Version ${BuildConfig.VERSION_NAME}"
        binding.updateButton.setOnClickListener { checkForUpdates() }
        binding.shareButton.setOnClickListener {
            startActivity(Intent.createChooser(Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"; putExtra(Intent.EXTRA_TEXT, "Check out Parking Spot Finder! https://github.com/jnetai-clawbot/ParkingSpot")
            }, "Share"))
        }
    }
    private fun checkForUpdates() {
        binding.updateStatus.text = "Checking..."
        Thread {
            try {
                val json = JSONObject(URL("https://api.github.com/repos/jnetai-clawbot/ParkingSpot/releases/latest").readText())
                val latest = json.optString("tag_name", "v0.0.0")
                runOnUiThread { binding.updateStatus.text = if (latest != "v${BuildConfig.VERSION_NAME}") "Update: $latest" else "Up to date!" }
            } catch (_: Exception) { runOnUiThread { binding.updateStatus.text = "Check failed" } }
        }.start()
    }
    override fun onSupportNavigateUp(): Boolean { finish(); return true }
}