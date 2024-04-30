package com.example.gaenari.activity.dactivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.R
import android.util.Log

class DFirstFragment : Fragment() {

    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var updateReceiver: BroadcastReceiver

    companion object {
        fun newInstance(): DFirstFragment {
            return DFirstFragment()
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dfirst, container, false)
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)

        setupUpdateReceiver()
        return view
    }

    // 업데이트 수신기 설정
    private fun setupUpdateReceiver() {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                Log.d("DFirstFragment", "Broadcast received")
                if (intent.action == "com.example.sibal.UPDATE_INFO") {
                    Log.d("DFirstFragment", "Data received: Distance ${intent.getDoubleExtra("distance", 0.0)}")
                    val distance = intent.getDoubleExtra("distance", 0.0)
                    val time = intent.getLongExtra("time", 0)
                    val heartRate = intent.getFloatExtra("heartRate", 0f)
                    val speed = intent.getFloatExtra("speed", 0f)
                    Log.d("DFirstFragment", "Received: Distance $distance, Time $time, Heart Rate $heartRate, Speed $speed")

                    // UI 요소 업데이트
                    distanceView.text = String.format("%.2f", distance)
                    timeView.text = formatTime(time)
                    heartRateView.text = String.format("%d", heartRate.toInt())
                    speedView.text = String.format("%.2f km/h", (speed*3.6))
                }
            }
        }
        val filter = IntentFilter("com.example.sibal.UPDATE_INFO")
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, filter)
    }


    // 밀리초를 시간 형식으로 포맷
    private fun formatTime(millis: Long): String {
        val hours = (millis / 3600000) % 24
        val minutes = (millis / 60000) % 60
        val seconds = (millis / 1000) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 브로드캐스트 리시버 등록 해제
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }
}
