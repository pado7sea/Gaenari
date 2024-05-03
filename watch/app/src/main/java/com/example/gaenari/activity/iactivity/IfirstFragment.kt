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
import com.example.gaenari.activity.main.Program
import com.example.gaenari.activity.result.ResultActivity

class IFirstFragment : Fragment() {
    private lateinit var nowProgram: Program
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CircleAdapter
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: ICircleProgress
    private lateinit var updateReceiver: BroadcastReceiver

    private var totalDistance: Double = 0.0
    private var totalHeartRate: Float = 0f
    private var heartRateCount: Int = 0
    private var totalTime: Long = 0
    private var nowSetCount: Int = 0
    private var nowExerciseCount: Int = 0
    private var setCount: Int = 0
    private var exerciseCount: Int = 0
    private var isTransitioning: Boolean = false

    companion object {
        fun newInstance(program: Program): IFirstFragment {
            return IFirstFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("program", program)
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
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
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.addItemDecoration(CustomItemDecoration(10))
    }

    private fun setupProgramData() {
        nowProgram = arguments?.getParcelable("program") ?: return
        setCount = nowProgram.program.intervalInfo.setCount!!
        exerciseCount = nowProgram.program.intervalInfo.rangeCount!!
        adapter = CircleAdapter(nowProgram.program.intervalInfo.ranges!!)
        recyclerView.adapter = adapter
    }

    private fun setupUpdateReceiver() {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == "com.example.sibal.UPDATE_INFO" && !isTransitioning) {
                    updateMetricsFromIntent(intent)
                    val remainingTime = calculateRemainingTime()
                    if (remainingTime <= 0) {
                        transitionToNextIntervalOrFinish(context)
                    } else {
                        updateUI()
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, IntentFilter("com.example.sibal.UPDATE_INFO"))
    }

    private fun calculateRemainingTime(): Long {
        val currentIntervalTotalTime = nowProgram.program.intervalInfo.ranges!![nowExerciseCount].time * 60  // 현재 인터벌 시간을 초로 변환
        return currentIntervalTotalTime - totalTime
    }
    private fun updateMetricsFromIntent(intent: Intent) {
        totalDistance = intent.getDoubleExtra("distance", 0.0)
        val heartRate = intent.getFloatExtra("heartRate", 0f)
        if (heartRate > 40) {
            totalHeartRate += heartRate
            heartRateCount++
        }
        totalTime = intent.getLongExtra("time", 0) / 1000  // 넘겨받은 시간을 초로 변환
    }
    private fun calculateRemainingTime(programTarget: Int): Long {
        return (programTarget * 3600000L / 3600) - totalTime
    }

    private fun transitionToNextIntervalOrFinish(context: Context) {
        isTransitioning = true
        vibrate(context)
        if (nowExerciseCount < exerciseCount - 1) {
            nowExerciseCount++
            totalTime = 0  // 리셋 현재 인터벌의 경과 시간
        } else if (nowSetCount < setCount - 1) {
            nowExerciseCount = 0
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
        distanceView.text = formatTime(totalTime)  // 총 경과 시간 표시
        timeView.text = String.format("%.2f km", totalDistance / 1000)
        heartRateView.text = String.format("%d bpm", (totalHeartRate / heartRateCount).toInt())
        speedView.text = String.format("%.2f km/h", calculateSpeed())
    }

    private fun calculateProgress(): Float {
        val totalMillis = nowProgram.program.intervalInfo.ranges!![nowExerciseCount].time * 60  // 초로 계산
        val remainingMillis = calculateRemainingTime()
        return 100f * (1 - remainingMillis.toFloat() / totalMillis)
    }

    private fun formatTime(seconds: Long): String {
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

        val averageHeartRate = if (heartRateCount > 0) totalHeartRate / heartRateCount else 0f
        val averageSpeed = if (totalTime > 0) (totalDistance / totalTime) * 3600 else 0.0

        val intent = Intent(context, ResultActivity::class.java).apply {

            putExtra("programTarget", programTarget)
            putExtra("programType", programType)
            putExtra("programTitle", programTitle)
            putExtra("programId", programId)
            putExtra("totalDistance", totalDistance)
            putExtra("averageHeartRate", averageHeartRate)
            putExtra("averageSpeed", averageSpeed)
            putExtra("totalTime", totalTime)
        }
        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }

    class CustomItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.right = space
        }
    }
}
