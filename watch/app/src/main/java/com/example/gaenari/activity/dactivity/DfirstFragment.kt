package com.example.gaenari.activity.dactivity

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
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.bumptech.glide.Glide
import com.example.gaenari.R
import com.example.gaenari.activity.result.ResultActivity
import com.example.gaenari.dto.request.SaveDataRequestDto

class DFirstFragment : Fragment() {
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: DCircleProgress
    private lateinit var updateReceiver: BroadcastReceiver
    private lateinit var requestDto: SaveDataRequestDto

    private var totalHeartRateAvg: Int = 0
    private var totalSpeedAvg: Double = 0.0
    private var totalDistance: Double = 0.0
    private var totalHeartRate: Float = 0f
    private var heartRateCount: Int = 0
    private var totalTime: Long = 0
    private var isPaused: Boolean = false

    companion object {
        fun newInstance(
            programTarget: Double,
            programType: String,
            programTitle: String,
            programId: Long
        ): DFirstFragment {
            val args = Bundle()
            args.putDouble("programTarget", programTarget)
            args.putString("programType", programType)
            args.putString("programTitle", programTitle)
            args.putLong("programId", programId)

            val fragment = DFirstFragment()
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dfirst, container, false)
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)
        val gifImageView = view.findViewById<ImageView>(R.id.gifImageView)
        context?.let {
            Glide.with(it)
                .asGif()
                .load(R.raw.dog98)
                .dontTransform()
                .into(gifImageView)
        }

        val programTarget = arguments?.getDouble("programTarget") ?: 0
        Log.d("first", "onCreateView: ${programTarget}")
        setupUpdateReceiver(programTarget as Double)
        return view
    }

    private fun setupUpdateReceiver(programTarget: Double) {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_INFO" -> {
                        val distance = intent.getDoubleExtra("distance", 0.0)
                        val remainingdistance = intent.getDoubleExtra("remainDistance", 0.0)
                        val speed = intent.getFloatExtra("speed", 0f)
                        val time = intent.getLongExtra("time", 0)
                        Log.d(
                            "d프래그먼트",
                            "distance: ${remainingdistance}, programtarget : ${programTarget}"
                        )
                        totalDistance = distance
                        totalTime = time

                        Log.d(
                            "distance",
                            "programTarget: ${programTarget * 1000} totalDistance: ${totalDistance}"
                        )
                        if (remainingdistance >= 0) {
                            updateUI(remainingdistance, programTarget, totalTime, speed)
                        }
                    }

                    "com.example.sibal.UPDATE_TIMER" -> {
                        val time = intent.getLongExtra("time", 0)
                        updateTimerUI(time)
                    }

                    "com.example.sibal.UPDATE_ONE_MINUTE" -> {
                        val checkspeed = intent.getDoubleExtra("(averageSpeed", 0.0)
                        val checkheart = intent.getIntExtra("averageHeartRate", 0)
                        val checkdistance = intent.getDoubleExtra("distance", 0.0)
                        Log.d("checkcheck", "${checkspeed} , ${checkheart}, ${checkdistance} ")
                        updateUIcheck(checkspeed, checkheart, checkdistance)
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
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(updateReceiver, intentFilter)
    }

    private fun updateUIcheck(checkspeed: Double, checkheart: Int, checkdistance: Double) {
    }

    private fun updateUI(
        remainingdistance: Double,
        programTarget: Double,
        time: Long,
        speed: Float
    ) {
        val totalMillis = programTarget
        val progress = 100 * (1 - (remainingdistance / totalMillis))
        circleProgress.setProgress(progress.toFloat())
        distanceView.text = String.format("%.2f km", (remainingdistance / 1000))
        speedView.text = String.format("%.2f", speed * 3.6)
    }

    private fun updateheartUI(heartRate: Float) {
        heartRateView.text = String.format("%d", heartRate.toInt())
    }


    //이거 남은거리로 바꿔야함
    private fun updateTimerUI(time: Long) {
        timeView.text = formatTime(time)
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
            val vibrationEffect =
                VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE)
            vibrator.vibrate(vibrationEffect)
        } else {
            vibrator.vibrate(500)
        }

        val programTarget = arguments?.getInt("programTarget") ?: 0
        val programType = arguments?.getString("programType") ?: ""
        val programTitle = arguments?.getString("programTitle") ?: ""
        val programId = arguments?.getLong("programId") ?: 0L

        val intent = Intent(context, ResultActivity::class.java).apply {
            putExtra("requestDto", requestDto)
            putExtra("programTarget", programTarget)
            putExtra("programType", programType)
            putExtra("programTitle", programTitle)
            putExtra("programId", programId)
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