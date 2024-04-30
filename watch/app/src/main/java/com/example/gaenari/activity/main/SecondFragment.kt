package com.example.gaenari

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*

class SecondFragment : Fragment() {
    private lateinit var stepCountTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var viewModel: StepCounterViewModel

    private lateinit var updateJob: Job // 코루틴 작업

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        stepCountTextView = view.findViewById(R.id.걸음수) // 걸음 수 텍스트뷰
        dateTextView = view.findViewById(R.id.date) // 날짜 텍스트뷰
        timeTextView = view.findViewById(R.id.time) // 시간 텍스트뷰

        // 백엔드 데이터와 함께 ViewModel 설정
        viewModel = ViewModelProvider(requireActivity()).get(StepCounterViewModel::class.java)
        viewModel.stepCount.observe(viewLifecycleOwner) { steps ->
            stepCountTextView.text = "걸음수: $steps" // 데이터 변경 시 UI 업데이트
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        startUpdatingTime() // 프래그먼트 활성화 시 시간 업데이트 시작
    }

    override fun onPause() {
        super.onPause()
        stopUpdatingTime() // 프래그먼트 비활성화 시 시간 업데이트 중단
    }

    private fun startUpdatingTime() {
        updateJob = CoroutineScope(Dispatchers.Main).launch {
            while (isActive) { // 코루틴이 활성화된 동안
                updateDateAndTime() // 날짜 및 시간 업데이트
                delay(1000) // 1초 간격으로 업데이트
            }
        }
    }

    private fun stopUpdatingTime() {
        if (::updateJob.isInitialized) {
            updateJob.cancel() // 코루틴 작업 취소
        }
    }

    private fun updateDateAndTime() {
        val koreaTimeZone = TimeZone.getTimeZone("Asia/Seoul") // 한국 시간대 설정
        val calendar = Calendar.getInstance(koreaTimeZone) // 한국 시간대로 캘린더 생성

        // 27초를 빼기
        calendar.add(Calendar.SECOND, -27)

        // 날짜 포맷 설정
        val dateFormat = SimpleDateFormat("M / d", Locale.getDefault())
        dateFormat.timeZone = koreaTimeZone
        val formattedDate = dateFormat.format(calendar.time)
        dateTextView.text = formattedDate

        // 시간 포맷 설정
        val timeFormat = SimpleDateFormat("HH : mm : ss", Locale.getDefault())
        timeFormat.timeZone = koreaTimeZone
        val formattedTime = timeFormat.format(calendar.time)
        timeTextView.text = formattedTime
    }
}
