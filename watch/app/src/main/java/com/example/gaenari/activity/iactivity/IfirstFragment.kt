package com.example.gaenari.activity.iactivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaenari.R
import com.example.gaenari.activity.result.ResultActivity
import com.example.gaenari.dto.request.HeartRates
import com.example.gaenari.dto.request.IntervalInfo
import com.example.gaenari.dto.request.Ranges
import com.example.gaenari.dto.request.Record
import com.example.gaenari.dto.request.SaveDataRequestDto
import com.example.gaenari.dto.request.Speeds
import com.example.gaenari.dto.response.FavoriteResponseDto
import java.time.LocalDate

class IFirstFragment : Fragment() {
    private lateinit var nowProgram: FavoriteResponseDto
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CircleAdapter
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: ICircleProgress
    private lateinit var updateReceiver: BroadcastReceiver


    private var totalHeartRateAvg: Float = 0f
    private var totalSpeedAvg: Double = 0.0
    private var curHeartRate: Float = 0f
    private var totalDistance: Double = 0.0
    private var totalTime: Long = 0
    private var heartRateCount: Int = 0
    private var nowSetCount: Int = 0
    private var nowExerciseCount: Int = 0
    private var setCount: Int = 0
    private var exerciseCount: Int = 0
    private var isTransitioning: Boolean = false

    private var startTimeOfCurrentInterval: Long = 0

    companion object {
        fun newInstance(program: FavoriteResponseDto): IFirstFragment {
            return IFirstFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("program", program)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ifirst, container, false)
        setupViews(view)
        setupRecyclerView(view)
        setupProgramData()
        setupUpdateReceiver()
        return view
    }

    private fun setupViews(view: View) {
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rvIntervals)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(CustomItemDecoration(10))
    }

    private fun setupProgramData() {
        nowProgram = arguments?.getParcelable("program", FavoriteResponseDto::class.java) ?: return
        setCount = nowProgram.program.intervalInfo?.setCount!!
        exerciseCount = nowProgram.program.intervalInfo?.rangeCount!!
        adapter = CircleAdapter(nowProgram.program.intervalInfo?.ranges!!)
        recyclerView.adapter = adapter
    }

    private fun setupUpdateReceiver() {

        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_INFO" -> {
                        val distance = intent.getDoubleExtra("distance", 0.0)
                        val speed = intent.getFloatExtra("speed", 0f)
                        val time = intent.getLongExtra("time", 0)

                        totalDistance = distance
                        totalTime = time
                        Log.d("Interval Activity", "onReceive: $time")
                    }
                    "com.example.sibal.UPDATE_TIMER" -> {
                        val time = intent.getLongExtra("time", 0)
                    }
                    "com.example.sibal.UPDATE_HEART_RATE" -> {
                        curHeartRate = intent.getFloatExtra("heartRate", 0f)
                    }
                    "com.example.sibal.UPDATE_RANGE_INFO" -> {
                        // 세트 내 n 번째 구간
                        val rangeIndex = intent.getIntExtra("rangeIndex", 0)
                        // 몇번째 세트
                        nowSetCount = intent.getIntExtra("setCount", 0)
                        // 걷기, 달리기 여부
                        val isRunning = intent.getBooleanExtra("isRunning", false)
                        // 현재 구간 총 시간
                        val rangeTime = intent.getLongExtra("rangeTime", 0)
                    }
                    "com.example.sibal.EXIT_INTERVAL_PROGRAM" -> {
                        totalHeartRateAvg = intent.getFloatExtra("totalHeartRateAvg", 0f)
                        totalSpeedAvg = intent.getDoubleExtra("totalSpeedAvg", 0.0)
                        sendResultsAndFinish(context)
                    }
                }
            }
        }
        val intentFilter = IntentFilter().apply {
            addAction("com.example.sibal.UPDATE_INFO")
            addAction("com.example.sibal.UPDATE_TIMER")
            addAction("com.example.sibal.UPDATE_HEART_RATE")
            addAction("com.example.sibal.UPDATE_RANGE_INFO")
            addAction("com.example.sibal.EXIT_INTERVAL_PROGRAM")
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, intentFilter)
    }

    private fun updateMetricsFromIntent(intent: Intent) {
        totalDistance = intent.getDoubleExtra("distance", 0.0)
        val heartRate = intent.getFloatExtra("heartRate", 0f)
        totalTime = intent.getLongExtra("time", 0) / 1000  // 넘겨받은 시간을 초로 변환
    }

    private fun calculateRemainingTime(): Double {
        val currentIntervalTime =
            nowProgram.program.intervalInfo?.ranges!![nowExerciseCount].time!! * 1000  // 초로 변환하여 계산
        val elapsedTime = System.currentTimeMillis() - startTimeOfCurrentInterval
        return currentIntervalTime - elapsedTime
    }

    private fun transitionToNextIntervalOrFinish(context: Context, intent: Intent) {
        isTransitioning = true
        vibrate(context)
        startTimeOfCurrentInterval = System.currentTimeMillis()
        if (nowExerciseCount < exerciseCount - 1) {
            nowExerciseCount++
            Log.d("인터벌", "transitionToNextIntervalOrFinish: ${nowExerciseCount}바뀜?")
            totalTime = 0  // 리셋 현재 인터벌의 경과 시간
        } else if (nowSetCount < setCount - 1) {
            nowExerciseCount = 0  // 인터벌을 처음부터 다시 시작
            Log.d("인터벌", "transitionToNextIntervalOrFinish: 다시시작함?")
            nowSetCount++
            totalTime = 0  // 리셋 현재 인터벌의 경과 시간
        } else {
            sendResultsAndFinish(context)
            return
        }
        adapter.updateActiveIndex(nowExerciseCount)
        setupUpdateReceiver()  // 리셋 리시버를 다시 설정하여 새 인터벌에 대한 타이밍을 재조정
        isTransitioning = false
    }

    private fun updateUI() {
        circleProgress.setProgress(calculateProgress())
        val remainingTime = calculateRemainingTime() / 1000
        distanceView.text = formatTime(remainingTime)  // 총 경과 시간 표시
        timeView.text = String.format("%.2f km", totalDistance / 1000)
        heartRateView.text = String.format("%d", curHeartRate)
        speedView.text = String.format("%.2f km/h", calculateSpeed())
    }

    private fun calculateProgress(): Double {
        val totalSeconds =
            nowProgram.program.intervalInfo?.ranges!![nowExerciseCount].time!! * 1000  // 분을 초로 변환하여 계산
        val remainingSeconds = calculateRemainingTime()
        return 100f * (1 - remainingSeconds.toFloat() / totalSeconds)
    }

    private fun formatTime(seconds: Double): String {
        val hours = (seconds / 3600)
        val minutes = (seconds % 3600) / 60
        val secs = seconds % 60
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
    }

    private fun calculateSpeed(): Float {
        return if (totalTime > 0) ((totalDistance / totalTime) * 3600).toFloat() else 0f
    }

    private fun vibrate(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

    private fun sendResultsAndFinish(context: Context) {
        val programTarget = arguments?.getInt("programTarget") ?: 0
        val programType = arguments?.getString("programType") ?: ""
        val programTitle = arguments?.getString("programTitle") ?: ""
        val programId = arguments?.getLong("programId") ?: 0L

        val intent = Intent(context, ResultActivity::class.java).apply {

            putExtra("programTarget", programTarget)
            putExtra("programType", programType)
            putExtra("programTitle", programTitle)
            putExtra("programId", programId)
            putExtra("totalDistance", totalDistance)
            putExtra("averageHeartRate", totalHeartRateAvg)
            putExtra("averageSpeed", totalSpeedAvg)
            putExtra("totalTime", totalTime)
        }
        startActivity(intent)
        Log.d("인터벌", "sendResultsAndFinish: $intent")
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }

    class CustomItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.right = space
        }
    }
}
