package com.example.gaenari.activity.tactivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.gaenari.R
import android.util.Log

class TActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private var programTarget: Int = 0 // 초기값 설정
    private var programType: String = "0" // 초기값 설정
    private var programId: Long = 0 // 초기값 설정
    private var programTitle: String = "" // 초기값 설정

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tactivity)

        programId = intent.getLongExtra("programId",0)
        programTitle = intent.getStringExtra("programTitle") ?: "기본값"
        programType = intent.getStringExtra("programType") ?: "기본값"
        programTarget = intent.getIntExtra("programTarget", 0) // Intent에서 programTarget 가져오기
        setupViewPager()
        Log.d("jinzza", "programTarget: ${programTarget}")

    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager2)
        val adapter = TFragmentStateAdapter(this, programTarget,programType,programTitle,programId)
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
            0 -> viewPager.setCurrentItem(2, false)
        }
    }
}

