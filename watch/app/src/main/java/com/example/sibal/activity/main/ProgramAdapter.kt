package com.example.sibal.activity.main

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.sibal.R
import android.util.Log
import com.example.sibal.CountdownActivity

class ProgramAdapter(private val programs: List<Program>) : RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_program, parent, false)
        return ProgramViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val program = programs[position]

        // 배경 리소스 설정
        val backgroundResource = when (program.type) {
            "D" -> R.drawable.distancecircle
            "T" -> R.drawable.timecircle
            "I" -> R.drawable.intercircle
            else -> R.drawable.circular_background
        }

        holder.icon.setBackgroundResource(backgroundResource)

        // 텍스트 뷰 업데이트
        val text = when (program.type) {
            "D" -> "거리"
            "T" -> "시간"
            "I" -> "인터벌"
            else -> "기본"
        }

        holder.menu.text = text

        // 아이콘 클릭 이벤트 추가
        holder.icon.setOnClickListener {
            val context = holder.itemView.context
            val intent = when (program.type) {
                "D" -> {
                    Log.d("ProgramAdapter", "Navigating to DActivity")  // 디버깅 메시지
                    Intent(context, CountdownActivity::class.java)
                }
                "T" -> {
                    Log.d("ProgramAdapter", "Navigating to TActivity")
                    Intent(context, CountdownActivity::class.java)
                }
                "I" -> {
                    Log.d("ProgramAdapter", "Navigating to IActivity")
                    Intent(context, CountdownActivity::class.java)
                }
                else -> null
            }

            if (intent != null) {
                Log.d("ProgramAdapter", "Sending program: $program")  // 전달되는 프로그램 정보
                intent.putExtra("programTitle", program.programTitle)
                intent.putExtra("programType", program.type)
                intent.putExtra("programTarget", program.program.intervalInfo.targetValue)
                Log.d("ProgramAdapter", "Sending program12: ${program.programTitle}")
                Log.d("ProgramAdapter", "Sending program23: ${program.type}")
                Log.d("ProgramAdapter", "Sending program34: ${program.program.intervalInfo.targetValue}")

                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int = programs.size

    class ProgramViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val icon: ImageView = view.findViewById(R.id.programIcon)
        val menu: TextView = view.findViewById(R.id.menu)
    }
}
