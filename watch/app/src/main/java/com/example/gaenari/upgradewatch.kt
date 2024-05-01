package com.example.gaenari

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Timer
import kotlin.concurrent.timer

class upgradewatch : AppCompatActivity(), View.OnClickListener {

    private  lateinit var btn_start : Button
    private  lateinit var btn_restart : Button
    private  lateinit var tv_minute : TextView
    private  lateinit var tv_second : TextView
    private  lateinit var tv_millisecond : TextView

    private var isRunning = false
    private var timer : Timer? =null
    private  var time = 0

    private lateinit var prefResuktEv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        enableEdgeToEdge()
        setContentView(R.layout.activity_watch_upgrade)
        btn_start = findViewById(R.id.btn_start)
        btn_restart=findViewById(R.id.btn_restart)
        tv_minute=findViewById(R.id.tv_minute)
        tv_second=findViewById(R.id.tv_second)
        tv_millisecond=findViewById(R.id.tv_millisecond)

        btn_start.setOnClickListener(this)
        btn_restart.setOnClickListener(this)

        val endBtn = findViewById<Button>(R.id.end_btn)

        prefResuktEv = findViewById(R.id.count)
        val prefs = getPreferences(MODE_PRIVATE)
        //정보 추출하기
        val v = prefs.getInt("KEY",0)
        //정보 저장하기
        val editor = prefs.edit()
        editor.putInt("KEY", v.toInt()+1)
        editor.apply()

        prefResuktEv.text="지금 카운트는 : $v"
        // end_btn 클릭 리스너 설정
        endBtn.setOnClickListener {
            showExitConfirmation() // 다이얼로그로 종료 확인
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun showExitConfirmation() {
        // 다이얼로그 생성
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("운동 종료")
            .setMessage("운동을 종료하시겠습니까?")
            .setPositiveButton("Yes") { dialog, _ ->
                super.onBackPressed() // 액티비티 종료 (뒤로 가기)
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss() // 다이얼로그 닫기
            }
            .create()

        alertDialog.show() // 다이얼로그 표시
    }
    override fun onClick(view: View?) {
        when(view?.id){
            R.id.btn_start ->{
                if(isRunning){
                    pause()
                }else{
                    start()
                }
            }
            R.id.btn_restart ->{
                restart()
            }
        }
    }

    private fun start(){
        btn_start.text = getString(R.string.btn_pause)
        isRunning=true
        val circularAnimationView = findViewById<CircularAnimationView>(R.id.circular_animation)

        timer = timer(period = 100) { // 1초마다 실행
            runOnUiThread {
                circularAnimationView.updateAngle(0.6f) // 60초 동안 한 바퀴 도는 각도 업데이트
            }
        }

        timer = timer(period = 10){
            //1000ms = 1s
            //0.01 time 1+
            time++
            val milli_second = time%100
            val second = (time % 6000) / 100
            val minute = time/6000

            runOnUiThread {
                if(isRunning){
                tv_millisecond.text = if(milli_second<10) ",0${milli_second}" else ".${milli_second}"
                tv_second.text = if(second<10) ":0${second}" else ":${second}"
                tv_minute.text = "${minute}"
                }

            }
            }


    }
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        Toast.makeText(this, "뒤로 가기 동작이 비활성화되었습니다.", Toast.LENGTH_SHORT).show()
        // 여기서 super.onBackPressed()를 호출하지 않습니다.
    }
    private  fun pause(){
        btn_start.text = getString(R.string.btn_start)
        val circularAnimationView = findViewById<CircularAnimationView>(R.id.circular_animation)
        runOnUiThread {
            circularAnimationView.updateAngle(-0.6f) // 60초 동안 한 바퀴 도는 각도 업데이트
        }

        isRunning=false
        timer?.cancel()
    }
    private  fun restart(){
        timer?.cancel()

        btn_start.text = getString(R.string.btn_start)

        isRunning=false
        time=0
        tv_millisecond.text=",00"
        tv_second.text=":00"
        tv_minute.text="00"
    }
}