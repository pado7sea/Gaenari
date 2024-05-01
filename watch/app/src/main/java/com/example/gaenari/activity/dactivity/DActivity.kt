package com.example.gaenari.activity.dactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.gaenari.R
import android.util.Log

class DActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var programTarget: Int = 0 // 초기값 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dactivity)

        programTarget = intent.getIntExtra("programTarget", 0) // Intent에서 programTarget 가져오기
        setupViewPager()
        Log.d("jinzza", "onCreate: 서비스시작은됨?")
        startRunningService()
    }
    override fun onDestroy() {
        stopRunningService() // 액티비티가 파괴될 때 서비스 종료
        super.onDestroy()
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager2)
        val adapter = DFragmentStateAdapter(this, programTarget)
        viewPager.adapter = adapter
        viewPager.setCurrentItem(1, false)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handlePageChange(position)
            }
        })
    }

    private fun handlePageChange(position: Int) {
        when (position) {
            1 -> viewPager.setCurrentItem(3, false)
            4 -> viewPager.setCurrentItem(2, false)
        }
    }

    private fun startRunningService() {
        val serviceIntent = Intent(this, DRunningService::class.java)
        startForegroundService(serviceIntent)
    }
    private fun stopRunningService() {
        val serviceIntent = Intent(this, DRunningService::class.java)
        stopService(serviceIntent)
    }
}
