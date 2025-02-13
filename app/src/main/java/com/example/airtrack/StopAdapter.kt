package com.example.airtrack

import Stop
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class StopAdapter(
    private val stops: List<Stop>,
    private var currentStopIndex: Int,
    private var isKm: Boolean
) : RecyclerView.Adapter<StopAdapter.StopViewHolder>() {

    fun updateCurrentStopIndex(newIndex: Int) {
        currentStopIndex = newIndex
    }

    fun updateUnits(newUnit: Boolean) {
        isKm = newUnit
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StopViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stop, parent, false)
        return StopViewHolder(view)
    }

    override fun onBindViewHolder(holder: StopViewHolder, position: Int) {
        val stop = stops[position]
        holder.bind(stop, position, currentStopIndex, isKm)
    }

    override fun getItemCount(): Int = stops.size

    class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val stopName: TextView = itemView.findViewById(R.id.stopName)
        private val distanceFromPrevious: TextView = itemView.findViewById(R.id.distanceFromPrevious)
        private val visaRequired: TextView = itemView.findViewById(R.id.visaRequired)
        private val timeFromPrevious: TextView = itemView.findViewById(R.id.timeFromPrevious)

        fun bind(stop: Stop, position: Int, currentStopIndex: Int, isKm: Boolean) {
            stopName.text = stop.name

            val distance = if (isKm) {
                stop.distanceFromPrevious
            } else {
                stop.distanceFromPrevious * 0.621371
            }
            distanceFromPrevious.text = "Distance: %.2f %s".format(distance, if (isKm) "km" else "mi")
            timeFromPrevious.text = "Time: %d hrs".format(stop.timeFromPrevious)

            visaRequired.visibility = View.VISIBLE
            visaRequired.text = "VISA"

            val visaBoxBackground = GradientDrawable()
            visaBoxBackground.cornerRadius = 8f.dpToPx(itemView.context)
            visaBoxBackground.setColor(ContextCompat.getColor(itemView.context, R.color.dark_background))

            visaRequired.background = visaBoxBackground

            when {
                position < currentStopIndex -> {
                    stopName.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    distanceFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    timeFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    visaRequired.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_background))
                    visaBoxBackground.setColor(ContextCompat.getColor(itemView.context, R.color.red))
                }
                position == currentStopIndex -> {
                    stopName.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_text))
                    distanceFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_text))
                    timeFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_text))
                    visaRequired.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_background))
                    visaBoxBackground.setColor(ContextCompat.getColor(itemView.context, R.color.light_text))
                }
                else -> {
                    stopName.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_grey_text))
                    distanceFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_grey_text))
                    timeFromPrevious.setTextColor(ContextCompat.getColor(itemView.context, R.color.light_grey_text))
                    visaRequired.setTextColor(ContextCompat.getColor(itemView.context, R.color.dark_background))
                    visaBoxBackground.setColor(ContextCompat.getColor(itemView.context, R.color.light_grey_text))
                }
            }
            if (!stop.visaRequired) {
                visaRequired.visibility = View.GONE
            }
        }

        private fun Float.dpToPx(context: Context): Float {
            return this * context.resources.displayMetrics.density
        }
    }
}
