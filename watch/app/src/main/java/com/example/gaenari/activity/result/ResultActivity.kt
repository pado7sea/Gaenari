package com.example.gaenari.activity.result

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import android.widget.Toast
import com.example.gaenari.R
import com.example.gaenari.activity.main.HomeActivity
import com.example.gaenari.dto.request.SaveDataRequestDto
import com.example.gaenari.dto.response.ApiResponseDto
import com.example.gaenari.util.AccessToken
import com.example.gaenari.util.Retrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultActivity : AppCompatActivity() {
    private lateinit var requestDto: SaveDataRequestDto

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
        requestDto = intent.getParcelableExtra("requestDto", SaveDataRequestDto::class.java)!!
        val programTitle = intent.getStringExtra("programTitle") ?: "운동 정보 없음"
        val totalTime = intent.getLongExtra("totalTime", 0)  // 밀리초를 받음
        val totalDistance = intent.getDoubleExtra("totalDistance", 0.0)
        val averageHeartRate = requestDto.heartrates.average
        val averageSpeed = requestDto.speeds.average

        // 시간 포맷 변경
        val hours = totalTime / 3600000
        val minutes = (totalTime % 3600000) / 60000
        val seconds = ((totalTime % 3600000) % 60000) / 1000
        val formattedTime = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        // TextViews에 값 설정
        titleTextView.text = programTitle
        timeTextView.text = formattedTime
        distanceTextView.text = String.format("%.2f km", totalDistance/1000)
        heartRateTextView.text = String.format("%d bpm", averageHeartRate)
        speedTextView.text = String.format("%.2f km/h", averageSpeed)

        val saveBtn = findViewById<Button>(R.id.end_btn)
        saveBtn.setOnClickListener {
            saveExerciseRecordData()
            val intent = Intent(this@ResultActivity, HomeActivity::class.java)
            startActivity(intent)
        }
    }



    /**
     * 운동 기록 저장 API 호출 및 결과 확인
     */
    private fun saveExerciseRecordData() {
        Log.d("Check", "Exercise Record Data : $requestDto")

        val call = Retrofit.getApiService()
            .saveRunningData(AccessToken.getInstance().accessToken, requestDto)

        call.enqueue(object : Callback<ApiResponseDto<String>> {
            override fun onResponse(
                call: Call<ApiResponseDto<String>>,
                response: Response<ApiResponseDto<String>>
            ) {
                if (response.body()?.status == "SUCCESS")
                    Toast.makeText(
                        this@ResultActivity,
                        "운동 기록 전송 성공",
                        Toast.LENGTH_SHORT
                    ).show()
                else
                    Toast.makeText(
                        this@ResultActivity,
                        response.body()?.message,
                        Toast.LENGTH_SHORT
                    ).show()
            }

            override fun onFailure(call: Call<ApiResponseDto<String>>, t: Throwable) {
                Toast.makeText(
                    this@ResultActivity,
                    "API 연결 실패.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}