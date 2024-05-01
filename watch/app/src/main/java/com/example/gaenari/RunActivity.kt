package com.example.gaenari

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.content.pm.PackageManager

class RunActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var heartRateSensor: Sensor? = null
    private var speedSensor: Sensor? = null

    private lateinit var distanceTextView: TextView
    private lateinit var heartRateTextView: TextView
    private lateinit var speedTextView: TextView
    private lateinit var timeTextView: TextView // 달리기 시간 표시

    private var stepCount = 0
    private var distance = 0.0
    private var heartRate = 0
    private var speed = 0.0
    private var runningTime = 0 // 달리기 시간

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run) // 레이아웃 설정

        // TextView 찾기
        distanceTextView = findViewById(R.id.달린거리)
        heartRateTextView = findViewById(R.id.심박수)
        speedTextView = findViewById(R.id.속도)
        timeTextView = findViewById(R.id.time) // 달리기 시간 TextView

        sensorManager = getSystemService(SensorManager::class.java)
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        speedSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) // 속도 추정

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 1)
        } else {
            startCountdown() // 5초 카운트다운 시작
        }
    }

    private fun startCountdown() {
        val countdownTimer = object : CountDownTimer(5000, 1000) { // 5초 카운트다운
            override fun onTick(millisUntilFinished: Long) {
                runOnUiThread {
                    timeTextView.text = "시작까지 ${millisUntilFinished / 1000}초"
                }
            }

            override fun onFinish() {
                runOnUiThread {
                    timeTextView.text = "달리기 시작!"
                }
                startRun() // 1분 달리기 시작
            }
        }

        countdownTimer.start() // 카운트다운 시작
    }

    private fun startRun() {
        // 센서 리스너 등록
        sensorManager.registerListener(this, stepCounterSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, heartRateSensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, speedSensor, SensorManager.SENSOR_DELAY_NORMAL)

        // 1분 달리기 타이머
        val runTimer = object : CountDownTimer(60000, 1000) { // 1분 타이머
            override fun onTick(millisUntilFinished: Long) {
                runningTime = ((60000 - millisUntilFinished) / 1000).toInt() // 남은 시간
                runOnUiThread {
                    timeTextView.text = "남은 시간: ${runningTime}초"
                    speedTextView.text = "속도: %.2f m/s".format(speed)
                    distanceTextView.text = "거리: %.2f m".format(distance)
                    heartRateTextView.text = "심박수: $heartRate bpm"
                }
            }

            override fun onFinish() {
                sensorManager.unregisterListener(this@RunActivity) // 센서 리스너 해제
                runOnUiThread {
                    Toast.makeText(this@RunActivity, "달리기 완료!", Toast.LENGTH_SHORT).show()
                    timeTextView.text = "달리기 종료"
                }
            }
        }

        runTimer.start() // 1분 달리기 시작
    }

    override fun onSensorChanged(event: SensorEvent) {
        when (event.sensor.type) {
            Sensor.TYPE_STEP_COUNTER -> {
                val currentStepCount = event.values[0].toInt()
                if (stepCount == 0) {
                    stepCount = currentStepCount // 초기값 설정
                }
                distance = (currentStepCount - stepCount) * 0.8 // 평균 걸음 길이로 계산
            }
            Sensor.TYPE_HEART_RATE -> {
                heartRate = event.values[0].toInt() // 심박수 데이터
            }
            Sensor.TYPE_ACCELEROMETER -> {
                val accelerationMagnitude = Math.sqrt(
                    (event.values[0] * event.values[1] * event.values[0] +
                            event.values[1] * event.values[1] +
                            event.values[2] * event.values[2]).toDouble()
                )
                speed = (accelerationMagnitude - SensorManager.GRAVITY_EARTH) * 9.8
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 정확도 변경 시 처리 (필요한 경우)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            startCountdown() // 권한 부여 후 카운트다운 시작
        } else {
            runOnUiThread {
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        sensorManager.unregisterListener(this) // 센서 리스너 해제
        super.onDestroy() // 액티비티 종료
    }
}