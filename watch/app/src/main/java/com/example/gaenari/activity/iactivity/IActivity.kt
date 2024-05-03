package com.example.gaenari.activity.iactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.gaenari.R
import com.example.gaenari.activity.main.Program

class IActivity : AppCompatActivity() {

    //프로그램 객체를 받아서왔다
    private lateinit var program : Program
    private lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        program = intent.getParcelableExtra("programData")!!
        Log.d("인터벌", "onCreate: ${program}")
        setContentView(R.layout.activity_iactivity)
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager2)
        val adapter = IFragmentStateAdapter(this, program)
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



    override fun onDestroy() {
        stopRunningService() // 액티비티가 파괴될 때 서비스 종료
        super.onDestroy()
    }
    private fun startIRunningService() {
        val serviceIntent = Intent(this, IRunningService::class.java)
        startForegroundService(serviceIntent)
    }

    private fun stopRunningService() {
        val serviceIntent = Intent(this, IRunningService::class.java)
        stopService(serviceIntent)
    }
}
