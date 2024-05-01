package com.example.gaenari.activity.tactivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.R
import com.example.gaenari.activity.dactivity.DCircleProgress
import com.example.gaenari.activity.dactivity.DFirstFragment
import com.example.gaenari.activity.dactivity.DRunningService
import com.example.gaenari.activity.result.ResultActivity

class TFirstFragment : Fragment() {
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: TCircleProgress
    private lateinit var updateReceiver: BroadcastReceiver

    private var totalDistance: Double = 0.0
    private var totalHeartRate: Float = 0f
    private var heartRateCount: Int = 0
    private var totalTime: Long = 0

    companion object {
        fun newInstance(
            programTarget: Int,
            programType: String,
            programTitle: String,
            programId: Long
        ): TFirstFragment {
            val args = Bundle()
            args.putInt("programTarget", programTarget)
            args.putString("programType", programType)
            args.putString("programTitle", programTitle)
            args.putLong("programId", programId)

            val fragment = TFirstFragment()
            fragment.arguments = args
            return fragment
        }
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tfirst, container, false)
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
                    totalTime = time

                    //여기서는 남은시간이야
                    val remainingTime =(programTarget*3600000/60 ) - time

                    if (remainingTime <= 0) {
                        sendResultsAndFinish(context)
                    } else {
                        updateUI(distance, programTarget,remainingTime, heartRate, speed)
                    }
                }
            }
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, IntentFilter("com.example.sibal.UPDATE_INFO"))
    }
    private fun updateUI(distance: Double, programTarget: Int, remainingTime: Long, heartRate: Float, speed: Float) {
        val totalMillis = programTarget * 3600000/60  // programTarget을 밀리초로 변환
        val progress = 100 * (1 - (remainingTime.toFloat() / totalMillis))
        circleProgress.setProgress(progress)

        distanceView.text = formatTime(remainingTime)
        timeView.text = String.format("%.2f km", distance/1000)
//        timeView.text = formatTime(time)
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
        Intent(context, TRunningService::class.java).also { serviceIntent ->
            context.stopService(serviceIntent)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }
}
