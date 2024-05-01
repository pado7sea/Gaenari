package com.example.gaenari

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import pl.droidsonroids.gif.GifImageView
import android.widget.TextView
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.widget.Toast
import android.util.Log
import android.widget.Button

class AddTodoActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var initialStepCount = -1
    private val PERMISSION_REQUEST_CODE = 100 // 권한 요청 코드

    private lateinit var prefResukt : TextView

    @SuppressLint("ResourceType", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_todo)
        val goButton = findViewById<Button>(R.id.go_btn)

        prefResukt = findViewById(R.id.checkcount)
        val prefs = getSharedPreferences(/* name = */ "upgradewatch", /* mode = */ MODE_PRIVATE)
        val v = prefs.getInt("KEY",0)
        prefResukt.text="지금 카운트는 : $v"
        goButton.setOnClickListener {
            // Intent를 사용해 다른 액티비티로 이동
            val intent = Intent(this, upgradewatch::class.java)
            startActivity(intent)
        }

        // 권한이 있는지 확인하고, 없으면 요청
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), PERMISSION_REQUEST_CODE)
        } else {
            initializeSensor()
        }
    }

    // 권한 요청 결과 처리
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeSensor() // 권한이 부여되면 센서 초기화
            } else {
                Toast.makeText(this, "권한이 필요합니다.", Toast.LENGTH_SHORT).show() // 권한 거부 시 알림
            }
        }
    }

    @SuppressLint("ResourceType")
    private fun initializeSensor() {
        val sharedPreferences = getSharedPreferences("step_counter_prefs", Context.MODE_PRIVATE)
        initialStepCount = sharedPreferences.getInt("initial_step_count", -1) // -1이면 앱 재시작

        sensorManager = getSystemService(SensorManager::class.java)
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        // 추가적인 초기화 작업
        val gifImageView = findViewById<GifImageView>(R.id.gifImageView)
        gifImageView.setImageResource(R.raw.runningj)
    }

    override fun onResume() {
        super.onResume()
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME) // 원하는 주기 설정
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, stepCounterSensor)

        val sharedPreferences = getSharedPreferences("step_counter_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("initial_step_count", initialStepCount)
        editor.apply()
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("StepCounter", "Sensor changed: ${event.values[0]}") // 센서 이벤트 로그 추가

        val textView = findViewById<TextView>(R.id.걸음수)

        if (initialStepCount == -1) {
            initialStepCount = event.values[0].toInt() // 초기 걸음 수 설정
        }

        val currentStepCount = event.values[0].toInt()
        val steps = currentStepCount - initialStepCount
        runOnUiThread {
            textView.text = "걸음수: $steps" // 메인 스레드에서 UI 업데이트
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 정확도 변경 시 처리
    }
}