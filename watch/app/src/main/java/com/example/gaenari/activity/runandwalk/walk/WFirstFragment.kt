// WFirstFragment.kt

package com.example.gaenari.activity.runandwalk.walk

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.R
import com.example.gaenari.activity.result.ResultActivity
import com.example.gaenari.activity.runandwalk.run.RFirstFragment
import com.example.gaenari.dto.request.SaveDataRequestDto

class WFirstFragment : Fragment() {
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: WCircleProgress
    private lateinit var updateReceiver: BroadcastReceiver
    private lateinit var gifImageView : pl.droidsonroids.gif.GifImageView
    private lateinit var requestDto: SaveDataRequestDto

    private var totalHeartRateAvg: Int = 0
    private var totalSpeedAvg: Double = 0.0
    private var totalDistance: Double = 0.0
    private var totalHeartRate: Float = 0f
    private var heartRateCount: Int = 0
    private var totalTime: Long = 0
    private var isPaused: Boolean = false

    companion object {
        fun newInstance(): WFirstFragment {
            return WFirstFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_w_first, container, false)
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)
        gifImageView = view.findViewById<pl.droidsonroids.gif.GifImageView>(R.id.gifImageView)

        setupUpdateReceiver()
        return view
    }
    private fun setupUpdateReceiver() {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_INFO" -> {
                        val distance = intent.getDoubleExtra("distance", 0.0)
                        val speed = intent.getFloatExtra("speed", 0f)
                        val time = intent.getLongExtra("time", 0)
                        if(speed>0.6){
                            updateGifForActivity(true)
                        }else{
                            updateGifForActivity(false)
                        }

                        totalDistance = distance
                        totalTime = time

                        Log.d("티액티비티", "onReceive: $time")

                        updateUI(distance, speed)
                    }
                    "com.example.sibal.UPDATE_TIMER" -> {
                        val time = intent.getLongExtra("time", 0)
                        updateTimerUI(time)
                    }
                    "com.example.sibal.UPDATE_ONE_MINUTE" -> {
                        val checkspeed = intent.getDoubleExtra("(averageSpeed",0.0)
                        val checkheart = intent.getIntExtra("averageHeartRate",0)
                        val checkdistance = intent.getDoubleExtra("distance",0.0)
                        Log.d("checkcheck", "${checkspeed} , ${checkheart}, ${checkdistance} ")
                        updateUIcheck(checkspeed,checkheart,checkdistance)
                    }
                    "com.example.sibal.UPDATE_HEART_RATE" -> {
                        val heartRate = intent.getFloatExtra("heartRate", 0f)
                        totalHeartRate += heartRate
                        if (heartRate > 40) {
                            heartRateCount++
                        }
                        updateheartUI(heartRate)
                    }
                    "com.example.sibal.EXIT_PROGRAM" -> {
                        Log.d("Check", "Receive Exit BroadCast")
                        requestDto = intent.getParcelableExtra("requestDto", SaveDataRequestDto::class.java)!!
                        totalHeartRateAvg = requestDto.heartrates.average
                        totalSpeedAvg = requestDto.speeds.average
                        sendResultsAndFinish(context)
                    }
                    "com.example.siball.PAUSE_PROGRAM" -> {
                        isPaused = intent.getBooleanExtra("isPause", false)
                    }
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction("com.example.sibal.UPDATE_INFO")
            addAction("com.example.sibal.UPDATE_TIMER")
            addAction("com.example.sibal.UPDATE_ONE_MINUTE")
            addAction("com.example.sibal.UPDATE_HEART_RATE")
            addAction("com.example.sibal.EXIT_PROGRAM")
            addAction("com.example.siball.PAUSE_PROGRAM")
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, intentFilter)
    }

    private fun updateUIcheck(checkspeed : Double,checkheart:Int,checkdistance:Double ){
    }
    private fun updateUI(distance: Double,  speed: Float) {
//        distanceView.text = formatTime(remainingTime)
        timeView.text = String.format("%.2f", distance / 1000)
        speedView.text = String.format("%.2f", speed * 3.6)
    }

    @SuppressLint("ResourceType")
    fun updateGifForActivity(isRunning: Boolean) {
        val resourceId = if (isRunning) {
            R.raw.run4  // 사용자가 달리기를 시작한 경우
        } else {
            R.raw.stop4 // 사용자가 달리기를 멈춘 경우
        }
        gifImageView.setImageResource(resourceId)
    }



    private fun updateheartUI(heartRate: Float) {
        heartRateView.text = String.format("%d", heartRate.toInt())
    }

    private fun updateTimerUI(time: Long ) {
        val progress = 100 * (1 - (time/ 3600000).toFloat())
        circleProgress.setProgress(progress)
        distanceView.text = formatTime(time)
    }

    private fun formatTime(millis: Long): String {
        val hours = (millis / 3600000) % 24
        val minutes = (millis / 60000) % 60
        val seconds = (millis / 1000) % 60
        val formattedTime = StringBuilder()

        if (hours > 0) {
            formattedTime.append("${hours}h ")
        }
        if (minutes > 0 || hours > 0) { // 시간이 있는 경우 0분도 표시
            formattedTime.append("${minutes}m ")
        }
        formattedTime.append("${seconds}s") // 초는 항상 표시

        return formattedTime.toString().trim()
    }

    private fun sendResultsAndFinish(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val vibrationEffect = VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(500)
        }


        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra("requestDto", requestDto)
            putExtra("programTarget", 0)
            putExtra("programType", "R")
            putExtra("programTitle", "자유달리기")
            putExtra("programId", 0)
            putExtra("totalDistance", totalDistance)
            putExtra("totalTime", totalTime)
        }

        startActivity(intent)
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }
}