package com.example.gaenari.activity.dactivity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.gaenari.R
import com.example.gaenari.data.DRunningViewModel
import com.example.gaenari.data.DRunningViewModelFactory
//import com.example.gaenari.databinding.ActivityDactivityBinding
import com.example.gaenari.service.LocationService
import com.example.gaenari.service.SensorService
import com.example.gaenari.service.TimerService
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityDactivityBinding
    private lateinit var drunningviewmodel: DRunningViewModel //
    private lateinit var viewPager: ViewPager2
    private var programTarget: Int = 0 // 초기값 설정
    private var programType: String = "0" // 초기값 설정
    private var programId: Long = 0 // 초기값 설정
    private var programTitle: String = "" // 초기값 설정

    private var sensorIntent: Intent? = null
    private var locationIntent: Intent? = null
    private var timerServiceIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityDactivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)

       //러닝서비스 여기다가 추가해주면 좋을거같아 api호출하는거!
        //prefs = PreferencesUtil.getEncryptedSharedPreferences(getApplicationContext());
        // ViewModelProvider로 ViewModel 가져오기

        // ViewModelFactory로 ViewModelProvider 초기화
        val factory = DRunningViewModelFactory(application)
        drunningviewmodel = ViewModelProvider(this, factory).get(DRunningViewModel::class.java)
        //현재날짜를 집어넣고싶다
        // 현재 날짜를 구하는 함수 호출
        val currentDate = getCurrentISO8601Date()
        drunningviewmodel.setDate(currentDate)
        programId = intent.getLongExtra("programId",0)
        drunningviewmodel.setProgramId(programId)
        programTitle = intent.getStringExtra("programTitle") ?: "기본값"
        drunningviewmodel.setProgramTitle(programTitle)
        programTarget = intent.getIntExtra("programTarget", 0) // Intent에서 programTarget 가져오기
        drunningviewmodel.setProgramTarget(programTarget)

        setupViewPager()

        // 위치서비스 포그라운드 실행
       locationIntent = Intent(this, LocationService::class.java)
        startForegroundService(locationIntent)
        // 센서서비스 포그라운드 실행
        sensorIntent = Intent(this, SensorService::class.java)
        startForegroundService(sensorIntent)
        // 타임서비스 포그라운드 실행
        timerServiceIntent = Intent(this, TimerService::class.java)
        startForegroundService(timerServiceIntent)


        Log.d("jinzza", "onCreate: 서비스시작은됨?")

    }
    override fun onDestroy() {
        stopService(sensorIntent) // 센서서비스 중지
        stopService(locationIntent) // 위치서비스 중지
        stopService(timerServiceIntent) // 타임서비스 중지
        Log.d("jinzza", "onDestroy: 파괴됨??")

        super.onDestroy()
    }

    private fun setupViewPager() {
        viewPager = findViewById(R.id.viewPager2)
        val adapter = DFragmentStateAdapter(this, programTarget,programType,programTitle,programId)
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

    //현재날짜를 구하는함수
    fun getCurrentISO8601Date(): String {
        val now = Instant.now()
        val formatter = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .withZone(ZoneOffset.UTC)
        return formatter.format(now)
    }
}
