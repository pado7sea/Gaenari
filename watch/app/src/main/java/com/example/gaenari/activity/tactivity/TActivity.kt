package com.example.gaenari.activity.tactivity

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.gaenari.R
import com.example.gaenari.viewmodel.RunViewModel
import java.util.concurrent.TimeUnit
import android.util.Log

class TActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var viewModel: RunViewModel
    private var running = true // 달리기 상태
    private var distance = 0.0 // 달린 거리
    private val handler = Handler(Looper.getMainLooper())

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedCreateState: Bundle?) {
        super.onCreate(savedCreateState)
        setContentView(R.layout.activity_tactivity) // D 액티비티 레이아웃

        viewPager = findViewById(R.id.viewPager2)
        tabLayout = findViewById(R.id.tabLayout2)
        val targetDistanceInt = intent.getIntExtra("programTarget", 0)
        val targetDistance = targetDistanceInt.toDouble()
        Log.d("TActivity", "Received targetDistance: $targetDistance")

        // 인텐트의 extras 내용 확인
        val extras = intent.extras
        if (extras != null) {
            for (key in extras.keySet()) {
                val value = extras.get(key)
                Log.d("TActivity", "Key: $key, Value: $value")
            }
        } else {
            Log.d("TActivity", "No extras found in the intent.")
        }

        // ViewModel 설정
        viewModel = ViewModelProvider(this).get(RunViewModel::class.java)
        viewModel.updateTargetDistance(targetDistance)

        // 프래그먼트 연결
        val adapter = TFragmentStateAdapter(this)
        viewPager.adapter = adapter

        // 탭 레이아웃 연결
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Control"
                1 -> tab.text = "Running Info"
                2 -> tab.text = "Control"
                3 -> tab.text = "Running Info"
            }
        }.attach()
        viewPager.setCurrentItem(1, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    val currentItem = viewPager.currentItem
                    val itemCount = viewPager.adapter?.itemCount ?: 0

                    if (currentItem == itemCount - 1) {
                        viewPager.setCurrentItem(1, false) // 마지막에서 첫 번째로 이동
                    }

                    if (currentItem == 0) {
                        viewPager.setCurrentItem(itemCount - 2, false) // 첫 번째에서 마지막으로 이동
                    }
                }
            }
        })

        startRunning() // 달리기 시작
    }

    private fun startRunning() {
        val runnable = object : Runnable {
            override fun run() {
                if (running) {
                    // 시간 업데이트
                    viewModel.incrementTime(1, TimeUnit.SECONDS)

                    // 달린 거리 업데이트
                    distance += 0.01 // 예: 0.01km

                    viewModel.updateDistance(distance)

                    handler.postDelayed(this, 1000) // 1초 간격으로 반복
                }
            }
        }

        handler.post(runnable)
    }

    fun pauseRunning() {
        running = false
    }

    fun resumeRunning() {
        running = true
    }

    fun stopRunning() {
        running = false
        Toast.makeText(this, "Running stopped", Toast.LENGTH_SHORT).show()
        finish() // 액티비티 종료
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null) // 모든 핸들러 콜백 제거
    }
}

