package com.example.gaenari

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.gaenari.util.PreferencesUtil
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class SecondFragment : Fragment() {
    private lateinit var stepCountTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var viewModel: StepCounterViewModel
    private lateinit var gifImageView: pl.droidsonroids.gif.GifImageView

    private lateinit var updateJob: Job // 코루틴 작업

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_second, container, false)
        stepCountTextView = view.findViewById(R.id.걸음수) // 걸음 수 텍스트뷰
        dateTextView = view.findViewById(R.id.date) // 날짜 텍스트뷰
        timeTextView = view.findViewById(R.id.time) // 시간 텍스트뷰
        gifImageView = view.findViewById<pl.droidsonroids.gif.GifImageView>(R.id.gifImageView)

        // 백엔드 데이터와 함께 ViewModel 설정
        viewModel = ViewModelProvider(requireActivity()).get(StepCounterViewModel::class.java)
        viewModel.stepCount.observe(viewLifecycleOwner) { steps ->
            stepCountTextView.text = "걸음수: $steps" // 데이터 변경 시 UI 업데이트
        }
        updateGifForActivity(context = requireContext())

        // gifImageView 클릭 이벤트 설정
        gifImageView.setOnClickListener {
            updateGifRandomly(context = requireContext())
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

    @SuppressLint("ResourceType")
    fun updateGifForActivity(context: Context) {
        val prefs = PreferencesUtil.getEncryptedSharedPreferences(context)
        val petId = prefs.getLong("petId", 0)  // Default value as 0 if not found
        val resourceId = context.resources.getIdentifier("walk${petId}", "raw", context.packageName)

        // resourceId가 0이 아니면 리소스가 존재하는 것이므로 이미지를 설정하고, 0이면 기본 이미지를 설정
        if (resourceId != 0) {
            gifImageView.setImageResource(resourceId)
        } else {
            // 예를 들어 기본 이미지로 설정
            gifImageView.setImageResource(R.raw.doghome)
        }
    }

    @SuppressLint("ResourceType")
    private fun updateGifRandomly(context: Context) {
        val prefs = PreferencesUtil.getEncryptedSharedPreferences(context)
        val petId = prefs.getLong("petId", 0)  // Default value as 0 if not found
        val actions = listOf("run", "walk", "sit", "stop", "lay")
        val randomAction = actions[Random.nextInt(actions.size)]
        val resourceId = context.resources.getIdentifier("${randomAction}${petId}", "raw", context.packageName)

        // resourceId가 0이 아니면 리소스가 존재하는 것이므로 이미지를 설정하고, 0이면 기본 이미지를 설정
        if (resourceId != 0) {
            gifImageView.setImageResource(resourceId)
        } else {
            // 예를 들어 기본 이미지로 설정
            gifImageView.setImageResource(R.raw.doghome)
        }
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
