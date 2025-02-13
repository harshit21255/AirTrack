package com.example.airtrack

import Stop
import android.content.Context
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    private lateinit var stops: List<Stop>
    private var currentStopIndex = 0
    private var totalDistance = 0.0
    private var distanceCovered = 0.0
    private var isKm = true

    private lateinit var progressBar: ProgressBar
    private lateinit var distanceCoveredText: TextView
    private lateinit var distanceLeftText: TextView
    private lateinit var journeySummaryText: TextView
    private lateinit var distanceToggleButton: MaterialButton
    private lateinit var nextStopButton: MaterialButton
    private lateinit var stopsRecyclerView: RecyclerView
    private lateinit var adapter: StopAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progressBar)
        distanceCoveredText = findViewById(R.id.distanceCovered)
        distanceLeftText = findViewById(R.id.distanceLeft)
        journeySummaryText = findViewById(R.id.journeySummary)
        distanceToggleButton = findViewById(R.id.distanceToggleButton)
        nextStopButton = findViewById(R.id.nextStopButton)
        stopsRecyclerView = findViewById(R.id.stopsRecyclerView)

        stops = readStopsFromFile(this)
        totalDistance = stops.sumOf { it.distanceFromPrevious }

        adapter = StopAdapter(stops, currentStopIndex, isKm)
        stopsRecyclerView.layoutManager = LinearLayoutManager(this)
        stopsRecyclerView.adapter = adapter

        updateUI()

        nextStopButton.setOnClickListener {
            if (currentStopIndex < stops.size - 1) {
                currentStopIndex++
                distanceCovered += stops[currentStopIndex].distanceFromPrevious
                updateUI()
            }
        }

        distanceToggleButton.setOnClickListener {
            isKm = !isKm
            adapter.updateUnits(isKm)
            updateUI()
        }
    }

    private fun readStopsFromFile(context: Context): List<Stop> {
        val stops = mutableListOf<Stop>()
        context.resources.openRawResource(R.raw.stops).bufferedReader().useLines { lines ->
            lines.forEach { line ->
                val parts = line.split(",")
                if (parts.size == 4) {
                    val name = parts[0].trim()
                    val distance = parts[1].trim().toDouble()
                    val visaRequired = parts[2].trim().equals("Yes", ignoreCase = true)
                    val time = parts[3].trim().toInt()
                    stops.add(Stop(name, distance, visaRequired, time))
                }
            }
        }
        return stops
    }

    private fun updateUI() {
        val progress = ((distanceCovered / totalDistance) * 100).toInt()
        progressBar.progress = progress

        val distanceLeft = totalDistance - distanceCovered
        distanceCoveredText.text = formatDistance(distanceCovered)
        distanceLeftText.text = formatDistance(distanceLeft)

        journeySummaryText.text = "📍${stops[currentStopIndex].name}"

        adapter.updateCurrentStopIndex(currentStopIndex)
        adapter.notifyDataSetChanged()

//        if (currentStopIndex >= (stopsRecyclerView.layoutManager?.itemCount ?: 0)) {
            stopsRecyclerView.smoothScrollToPosition(currentStopIndex)
//        }
    }

    private fun formatDistance(distance: Double): String {
        return if (isKm) {
            "%.2f km".format(distance)
        } else {
            "%.2f mi".format(distance * 0.621371)
        }
    }
}