package com.example.gaenari.activity.iactivity

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gaenari.dto.response.RangeDto

class CircleAdapter(private var ranges: List<RangeDto>, private var currentIndex: Int = 0) :
    RecyclerView.Adapter<CircleAdapter.ViewHolder>() {

    class ViewHolder(val circleView: CircleView) : RecyclerView.ViewHolder(circleView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val circleView = CircleView(parent.context)
        return ViewHolder(circleView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isActive = position == currentIndex
        holder.circleView.updateView(ranges[position].isRunning!!, isActive)
    }

    override fun getItemCount() = ranges.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateIndex(newIndex: Int) {
        currentIndex = newIndex
        notifyDataSetChanged()
    }
}