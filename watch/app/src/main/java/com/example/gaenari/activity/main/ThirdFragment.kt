package com.example.gaenari.activity.main

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.PagerSnapHelper
import android.widget.TextView
import com.example.gaenari.R

class ThirdFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var programDetails: TextView
    private lateinit var programTitle: TextView
    private val mockData = createMockData()

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        programDetails = view.findViewById(R.id.programDetails)
        programTitle = view.findViewById(R.id.programTitle)

        recyclerView.isVerticalScrollBarEnabled = true
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // 어댑터 초기화
        recyclerView.adapter = ProgramAdapter(mockData)

        // 스크롤 리스너 추가
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val position = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2
                val program = mockData[position]
                val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                val totalItemCount = layoutManager.itemCount

                val scrollProgress = (firstVisiblePosition.toFloat() / (totalItemCount - 1) * 100).toInt()
                val progressBar = view.findViewById<ProgressBar>(R.id.verticalProgressBar)
                progressBar.progress = scrollProgress

                // 중앙에 있는 아이템의 상세 정보 업데이트
                updateProgramDetails(program)
            }
        })

        return view
    }

    private fun updateProgramDetails(program: Program) {
        programTitle.text = when (program.type) {
            "D" -> "${program.programTitle}"
            "T" -> "${program.programTitle}"
            "I" -> "${program.programTitle}"
            else -> "${program.programTitle}"
        }

        programDetails.text = when (program.type) {
            "D" -> "목표 거리\n${program.program.intervalInfo.targetValue} KM"
            "T" -> "목표 시간\n${program.program.intervalInfo.targetValue} 시간"
            "I" -> "세트 수\n${program.program.intervalInfo.rangeCount} 세트"
            else -> "${program.programTitle}"
        }
    }
}
