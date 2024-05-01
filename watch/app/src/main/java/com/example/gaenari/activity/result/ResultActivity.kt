package com.example.gaenari.activity.result

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.example.gaenari.R

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // TextViews 찾기
        val titleTextView = findViewById<TextView>(R.id.운동제목)
        val timeTextView = findViewById<TextView>(R.id.시간)
        val distanceTextView = findViewById<TextView>(R.id.거리)
        val heartRateTextView = findViewById<TextView>(R.id.심박수)
        val speedTextView = findViewById<TextView>(R.id.속도)

        // 인텐트에서 데이터 가져오기
        val programTitle = intent.getStringExtra("programTitle") ?: "운동 정보 없음"
        val totalTime = intent.getLongExtra("totalTime", 0)  // 밀리초를 받음
        val totalDistance = intent.getDoubleExtra("totalDistance", 0.0)
        val averageHeartRate = intent.getFloatExtra("averageHeartRate", 0f)
        val averageSpeed = intent.getDoubleExtra("averageSpeed", 0.0)

        // 시간 포맷 변경
        val hours = totalTime / 3600000
        val minutes = (totalTime % 3600000) / 60000
        val seconds = ((totalTime % 3600000) % 60000) / 1000
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        // TextViews에 값 설정
        titleTextView.text = programTitle
        timeTextView.text = formattedTime
        distanceTextView.text = String.format("%.2f km", totalDistance)
        heartRateTextView.text = String.format("%.0f bpm", averageHeartRate)
        speedTextView.text = String.format("%.2f km/h", averageSpeed)
    }
}