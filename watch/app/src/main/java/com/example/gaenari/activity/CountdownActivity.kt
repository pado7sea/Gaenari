package com.example.gaenari.activity

import android.os.Bundle
import android.os.CountDownTimer
import android.content.Intent
import android.widget.TextView
import android.widget.ImageView
import android.animation.ObjectAnimator
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import com.example.gaenari.activity.dactivity.DActivity
import com.example.gaenari.activity.iactivity.IActivity
import com.example.gaenari.activity.tactivity.TActivity
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.gaenari.R
import com.example.gaenari.activity.dactivity.DistTargetService
import com.example.gaenari.activity.iactivity.IntervalService
import com.example.gaenari.activity.runandwalk.run.RService
import com.example.gaenari.activity.runandwalk.run.RunningActivity
import com.example.gaenari.activity.runandwalk.walk.WService
import com.example.gaenari.activity.runandwalk.walk.WalkingActivity
import com.example.gaenari.activity.tactivity.TimeTargetService
import com.example.gaenari.dto.request.AlertStartRequestDto
import com.example.gaenari.dto.response.ApiResponseDto
import com.example.gaenari.dto.response.FavoriteResponseDto
import com.example.gaenari.util.AccessToken
import com.example.gaenari.util.Retrofit
import com.example.gaenari.util.TTSUtil

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.time.LocalDateTime

class CountdownActivity : AppCompatActivity() {
    override fun onCreate(savedCreateState: Bundle?) {
        super.onCreate(savedCreateState)
        setContentView(R.layout.activity_countdown)

        val countdownTextView = findViewById<TextView>(R.id.countdownTextView)
        val imageView = findViewById<ImageView>(R.id.imageView)
        val title = findViewById<TextView>(R.id.title)


        val programType = intent.getStringExtra("programType")
        val programTitle = intent.getStringExtra("programTitle")
        title.text = programTitle

        val textSizeStart = 90f
        val textSizeEnd = 75f
        val textColorStart = ContextCompat.getColor(this, R.color.blue)
        val textColorEnd = ContextCompat.getColor(this, R.color.gray2)
        val borderColorStart = ContextCompat.getColor(this, R.color.countdown1)
        val borderColorEnd = ContextCompat.getColor(this, R.color.lightgreen)
        val animationDuration = 1000L

        object : CountDownTimer(6000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                TTSUtil.speak("멍!")

                val secondsRemaining = (millisUntilFinished / 1000).toInt()
                countdownTextView.text = secondsRemaining.toString()
                // 텍스트 크기 애니메이션
                val textSizeAnimator = ObjectAnimator.ofFloat(
                    countdownTextView, "textSize", textSizeStart, textSizeEnd
                )
                textSizeAnimator.duration = animationDuration
                textSizeAnimator.repeatCount = 1
                textSizeAnimator.repeatMode = ObjectAnimator.REVERSE
                textSizeAnimator.start()

                // 텍스트 색상 애니메이션
                val textColorAnimator = ObjectAnimator.ofObject(
                    countdownTextView, "textColor", ArgbEvaluator(), textColorStart, textColorEnd
                )
                textColorAnimator.duration = animationDuration
                textColorAnimator.repeatCount = 1
                textColorAnimator.repeatMode = ObjectAnimator.REVERSE
                textColorAnimator.start()

                // 이미지뷰 배경색 애니메이션
                val imageColorAnimator = ValueAnimator.ofObject(
                    ArgbEvaluator(), borderColorStart, borderColorEnd
                )
                imageColorAnimator.duration = animationDuration
                imageColorAnimator.repeatCount = 1
                imageColorAnimator.addUpdateListener { animator ->
                    val color = animator.animatedValue as Int
                    imageView.setBackgroundColor(color)
                }
                imageColorAnimator.start()
            }

            override fun onFinish() {
                val intent = when (programType) {
                    "D" -> Intent(this@CountdownActivity, DActivity::class.java)
                    "T" -> Intent(this@CountdownActivity, TActivity::class.java)
                    "I" -> Intent(this@CountdownActivity, IActivity::class.java)
                    "W" -> Intent(this@CountdownActivity, WalkingActivity::class.java)
                    "R" -> Intent(this@CountdownActivity, RunningActivity::class.java)
                    else -> null
                }

                if (intent != null) {
                    intent.putExtras(this@CountdownActivity.intent.extras ?: Bundle()) // 인텐트 데이터 복사
                    Log.d("Check Countdown Activity", "잘가니? : $intent")

                    val serviceIntent = when (programType) {
                        "D" -> Intent(this@CountdownActivity, DistTargetService::class.java)
                        "T" -> Intent(this@CountdownActivity, TimeTargetService::class.java)
                        "I" -> Intent(this@CountdownActivity, IntervalService::class.java)
                        "W" -> Intent(this@CountdownActivity, WService::class.java)
                        "R" -> Intent(this@CountdownActivity, RService::class.java)
                        else -> null
                    }

                    sendAlert()
                    startExerciseService(serviceIntent)
                    startActivity(intent) // 다음 액티비티 시작
                }

                finish()
            }
        }.start()
    }

    private fun sendAlert() {
        val token = AccessToken.getInstance().accessToken
        val program = intent.getParcelableExtra("programData", FavoriteResponseDto::class.java)!!

        Log.d("Check Countdown Activity", "ProgramData : $program")

        val requestDto = AlertStartRequestDto(
            exerciseDateTime = LocalDateTime.now(),
            programTitle = program.programTitle,
            programType = program.type
        )

        Log.d("Check Countdown Activity", "AlertStartRequest : $requestDto")

        val call = Retrofit.getApiService().alertStartExercise(token, requestDto)

        call.enqueue(object : Callback<ApiResponseDto<String>> {
            override fun onResponse(
                call: Call<ApiResponseDto<String>>,
                response: Response<ApiResponseDto<String>>
            ) {
                if (response.body()?.status == "ERROR") {
                    Log.d("Check Countdown Activity", "Send Notice Fail")
                } else {
                    Log.d("Check Countdown Activity", "Send Notice Success")
                }
            }

            override fun onFailure(
                call: Call<ApiResponseDto<String>>, t: Throwable
            ) {
                Log.d("Check Countdown Activity", "Send Notice Unforeseen Error")
            }
        })
    }

    /**
     * 운동 프로그램 서비스 시작
     */
    private fun startExerciseService(serviceIntent: Intent?) {
        serviceIntent?.putExtra(
            "programData",
            intent.getParcelableExtra("programData", FavoriteResponseDto::class.java)
        )
        Log.d("Check Countdown Activity", "StartExerciseService: $serviceIntent")
        startForegroundService(serviceIntent)
    }
}
