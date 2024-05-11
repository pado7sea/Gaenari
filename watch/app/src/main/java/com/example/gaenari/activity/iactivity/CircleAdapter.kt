package com.example.gaenari.activity.iactivity

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gaenari.R
import com.example.gaenari.activity.main.Range
import com.example.gaenari.dto.request.Ranges
import com.example.gaenari.dto.response.RangeDto

class CircleAdapter(private var ranges: List<RangeDto>) :
    RecyclerView.Adapter<CircleAdapter.CircleViewHolder>() {

    class CircleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val circleView: View = view.findViewById(R.id.view_circle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CircleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_circle, parent, false)
        return CircleViewHolder(view)
    }

    override fun onBindViewHolder(holder: CircleViewHolder, position: Int) {
        val range = ranges[position]
        holder.circleView.background.setColorFilter(
            if (range.isRunning == true) Color.GREEN else Color.BLUE, PorterDuff.Mode.SRC_IN
        )
        val layoutParams = holder.circleView.layoutParams as ViewGroup.LayoutParams
        layoutParams.width = 30
        layoutParams.height = layoutParams.width
        holder.circleView.layoutParams = layoutParams
    }

    override fun getItemCount() = ranges.size

    var currentActiveIndex = -1 // 현재 활동 중인 원의 인덱스

    fun updateActiveIndex(newActiveIndex: Int) {
        currentActiveIndex = newActiveIndex
        notifyDataSetChanged()
    }
}