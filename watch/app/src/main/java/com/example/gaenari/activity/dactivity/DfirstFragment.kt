package com.example.gaenari.activity.dactivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.R
import com.example.gaenari.activity.result.ResultActivity


class DFirstFragment : Fragment() {

    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: DCircleProgress
    private lateinit var updateReceiver: BroadcastReceiver

    private var totalDistance: Double = 0.0
    private var totalHeartRate: Float = 0f
    private var heartRateCount: Int = 0
    private var totalTime: Long = 0

    companion object {
        fun newInstance(programTarget: Int, programType: String, programTitle: String, programId: Long): DFirstFragment {
            val args = Bundle()
            args.putInt("programTarget", programTarget)
            args.putString("programType", programType)
            args.putString("programTitle", programTitle)
            args.putLong("programId", programId)

            val fragment = DFirstFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dfirst, container, false)
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)

        val programTarget = arguments?.getInt("programTarget") ?: 0
        Log.d("first", "onCreateView: ${programTarget}")
        setupUpdateReceiver(programTarget)
        return view
    }

    private fun setupUpdateReceiver(programTarget: Int) {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (intent.action == "com.example.sibal.UPDATE_INFO") {
                    val distance = intent.getDoubleExtra("distance", 0.0)
                    val time = intent.getLongExtra("time", 0)
                    val heartRate = intent.getFloatExtra("heartRate", 0f)
                    val speed = intent.getFloatExtra("speed", 0f)

                    // 총계 및 횟수 업데이트
                    //ㄴㄴ이미 나는 총 뛴거리를 보내주고있어
                    totalDistance = distance
                    totalHeartRate += heartRate
                    if (heartRate > 40) {
                        heartRateCount++
                    }
                    totalTime = time-5000

                    val remainingDistance =(programTarget * 10).toDouble() - distance
//                   이거로바꿔야한다!!!!!! val remainingDistance =(programTarget * 1000).toDouble() - distance

                    if (remainingDistance <= 0) {
                        sendResultsAndFinish(context)
                    } else {
                        updateUI(remainingDistance, programTarget,totalTime, heartRate, speed)
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, IntentFilter("com.example.sibal.UPDATE_INFO"))
    }

    private fun updateUI(remainingDistance: Double, programTarget: Int, totalTime: Long, heartRate: Float, speed: Float) {
//       이거로바꿔야힌디!!! val progress = (100 - (remainingDistance / (programTarget * 1000) * 100)).toFloat()
        val progress = (100 - (remainingDistance / (programTarget * 10) * 100)).toFloat()
        circleProgress.setProgress(progress) // 원형 프로그레스 업데이트

        distanceView.text = String.format("%.2f", remainingDistance/1000)
        timeView.text = formatTime(totalTime)
        heartRateView.text = String.format("%d", heartRate.toInt())
        speedView.text = String.format("%.2f km/h", speed * 3.6)
    }
    private fun formatTime(millis: Long): String {
        val hours = (millis / 3600000) % 24
        val minutes = (millis / 60000) % 60
        val seconds = (millis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    private fun sendResultsAndFinish(context: Context) {

        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        // SDK 버전에 따른 조건 처리
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Android O 이상에서는 VibrationEffect를 사용
            val vibrationEffect = VibrationEffect.createOneShot(
                500, // 500 밀리초 동안 진동
                VibrationEffect.DEFAULT_AMPLITUDE // 진동 강도
            )
            vibrator.vibrate(vibrationEffect)
        } else {
            // Android O 미만은 기본 진동 함수 사용
            vibrator.vibrate(500) // 500 밀리초 동안 진동
        }

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

        // 서비스 중지
        Intent(context, DRunningService::class.java).also { serviceIntent ->
            context.stopService(serviceIntent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }
}
