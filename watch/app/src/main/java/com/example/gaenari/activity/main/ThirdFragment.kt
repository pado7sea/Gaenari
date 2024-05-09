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
import androidx.lifecycle.ViewModelProvider
import com.example.gaenari.R
import com.example.gaenari.model.SharedViewModel
import com.example.gaenari.dto.response.FavoriteResponseDto

class ThirdFragment : Fragment() {
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var programDetails: TextView
    private lateinit var programTitle: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_third, container, false)

        // 뷰 초기화
        recyclerView = view.findViewById(R.id.recyclerView)
        programDetails = view.findViewById(R.id.programDetails)
        programTitle = view.findViewById(R.id.programTitle)

        // 리사이클러 뷰 레이아웃 및 스냅 도우미 설정
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)

        // ViewModel 초기화 및 데이터 구독
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        sharedViewModel.favoritePrograms.observe(viewLifecycleOwner) { programs ->
            // 어댑터를 업데이트합니다.
            recyclerView.adapter = ProgramAdapter(programs)

            // 아이템 스크롤 리스너 추가
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    val position = (layoutManager.findFirstVisibleItemPosition() + layoutManager.findLastVisibleItemPosition()) / 2
                    if (position in programs.indices) {
                        val program = programs[position]
                        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                        val totalItemCount = layoutManager.itemCount

                        val scrollProgress = (firstVisiblePosition.toFloat() / (totalItemCount - 1) * 100).toInt()
                        val progressBar = view.findViewById<ProgressBar>(R.id.verticalProgressBar)
                        progressBar.progress = scrollProgress

                        // 중앙에 있는 아이템의 상세 정보 업데이트
                        updateProgramDetails(program)
                    }
                }
            })
        }

        return view
    }

    private fun updateProgramDetails(program: FavoriteResponseDto) {
        programTitle.text = program.programTitle ?: "제목 없음"

        val programTypeInfo = program.program

        val programDetailsText = when (program.type) {
            "D" -> {
                val targetValue = programTypeInfo?.targetValue ?: "정보 없음"
                "목표 거리\n${targetValue} KM"
            }
            "T" -> {
                // null이 아닐 때만 연산하도록 변경
                val targetValueInMinutes = programTypeInfo?.targetValue?.toInt()?.div(60) ?: "정보 없음"
                "목표 시간\n${targetValueInMinutes} 분"
            }
            "I" -> {
                val rangeCount = programTypeInfo?.intervalInfo?.rangeCount ?: "정보 없음"
                "세트 수\n${rangeCount} 세트"
            }
            else -> "타입 알 수 없음"
        }

        programDetails.text = programDetailsText
    }
}
