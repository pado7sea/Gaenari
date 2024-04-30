package com.example.sibal.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.Manifest
import androidx.core.content.ContextCompat
import androidx.core.app.ActivityCompat
import android.widget.Toast
import com.example.sibal.R
import com.example.sibal.StepCounterViewModel

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: MyFragmentStateAdapter
    private lateinit var sensorManager: SensorManager
    private var stepCounterSensor: Sensor? = null
    private var initialStepCount = -1
    private lateinit var viewModel: StepCounterViewModel
    private val PERMISSION_REQUEST_CODE = 100 // 권한 요청 코드

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)

        // 프래그먼트 어댑터 및 ViewPager2 설정
        adapter = MyFragmentStateAdapter(this)  // 어댑터 설정
        viewPager.adapter = adapter  // 어댑터 연결

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Third"
                1 -> tab.text = "First"
                2 -> tab.text = "Second"
                3 -> tab.text = "Third"
                4 -> tab.text = "First"
            }
        }.attach()  // 탭과 ViewPager2 연동

        // 두 번째 페이지로 초기 설정
        viewPager.setCurrentItem(2, false)

        // 원형 스크롤 구현
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val currentItem = viewPager.currentItem
                    val itemCount = viewPager.adapter?.itemCount ?: 0

                    if (currentItem == itemCount - 1) {
                        viewPager.setCurrentItem(1, false)  // 마지막에서 첫 번째로 이동
                    }

                    if (currentItem == 0) {
                        viewPager.setCurrentItem(itemCount - 2, false)  // 첫 번째에서 마지막으로 이동
                    }
                }
            }
        })

        // 권한 요청 처리
        requestPermissionsIfNeeded()

        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(StepCounterViewModel::class.java)
    }

    private fun requestPermissionsIfNeeded() {
        val permissionsToRequest = mutableListOf<String>()

        // Activity Recognition 권한 확인
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACTIVITY_RECOGNITION)
        }

        // Fine Location 권한 확인
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSION_REQUEST_CODE
            )
        } else {
            initializeSensor()  // 권한이 모두 부여되었을 때 센서 초기화
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            var allPermissionsGranted = true

            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }

            if (allPermissionsGranted) {
                initializeSensor()  // 권한 부여 시 센서 초기화
            } else {
                Toast.makeText(this, "필요한 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()  // 권한 거부 시
            }
        }
    }

    private fun initializeSensor() {
        sensorManager = getSystemService(SensorManager::class.java)
        stepCounterSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onResume() {
        super.onResume()
        stepCounterSensor?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)  // 센서 리스너 등록
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this, stepCounterSensor)  // 센서 리스너 해제
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (initialStepCount == -1) {
            initialStepCount = event.values[0].toInt()  // 초기 걸음 수 설정
        }

        val currentStepCount = event.values[0].toInt()
        val steps = currentStepCount - initialStepCount

        viewModel.updateStepCount(steps)  // ViewModel로 데이터 업데이트
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // 센서 정확도 변경 시 처리
    }
}
