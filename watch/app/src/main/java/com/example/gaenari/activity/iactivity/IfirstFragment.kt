package com.example.gaenari.activity.iactivity

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Rect
import android.os.Build
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gaenari.R
import com.example.gaenari.activity.result.ResultActivity
import com.example.gaenari.dto.request.SaveDataRequestDto
import com.example.gaenari.dto.response.FavoriteResponseDto

class IFirstFragment : Fragment() {
    private lateinit var nowProgram: FavoriteResponseDto
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CircleAdapter
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var setView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: ICircleProgress
    private lateinit var updateReceiver: BroadcastReceiver
    private lateinit var requestDto: SaveDataRequestDto
    //gif
    private lateinit var gifImageView : pl.droidsonroids.gif.GifImageView

    private var totalHeartRateAvg: Int = 0
    private var totalSpeedAvg: Double = 0.0
    private var curHeartRate: Float = 0f
    private var totalDistance: Double = 0.0
    private var totalTime: Long = 0
    private var heartRateCount: Int = 0
    private var nowSetCount: Int = 0
    private var nowExerciseCount: Int = 0
    private var setCount: Int = 0
    private var exerciseCount: Int = 0
    private var isTransitioning: Boolean = false
    //이번인터벌의 내가 정한 속도
    private var nowExerciseSpeed: Double = 0.0
    //이번인터벌이 뛰니 안뛰니
    private var nowisRunning : Boolean = false
    //이제 해야할 운동의 시간
    private var nowecercisetime : Long = 0
    //리얼타임을 계산할거
    private var realtime : Long = 0

    private var startTimeOfCurrentInterval: Long = 0
    private var isPaused: Boolean = false

    companion object {
        fun newInstance(program: FavoriteResponseDto): IFirstFragment {
            return IFirstFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("program", program)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_ifirst, container, false)
        setupViews(view)
        setupProgramData()
        setupRecyclerView(view)
        setupUpdateReceiver()
        return view
    }

    private fun setupViews(view: View) {
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)
        gifImageView = view.findViewById<pl.droidsonroids.gif.GifImageView>(R.id.gifImageView)
        setView = view.findViewById(R.id.setname)
    }

    private fun setupRecyclerView(view: View) {
        recyclerView = view.findViewById(R.id.rvIntervals)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
//        recyclerView.addItemDecoration(CustomItemDecoration(10))
        adapter = CircleAdapter(nowProgram.program.intervalInfo!!.ranges!!, 0)
        recyclerView.adapter = adapter
    }

    private fun setupProgramData() {
        nowProgram = arguments?.getParcelable("program", FavoriteResponseDto::class.java) ?: return
        Log.d("Interval Activity", "setupProgramData: ${nowProgram}")
        setCount = nowProgram.program.intervalInfo?.setCount!!
        exerciseCount = nowProgram.program.intervalInfo?.rangeCount!!
        nowExerciseSpeed = nowProgram.program.intervalInfo?.ranges!![0].speed!!
        nowisRunning=nowProgram.program.intervalInfo?.ranges!![0].isRunning!!
        nowecercisetime= nowProgram.program.intervalInfo?.ranges!![0].time?.toLong()!!*1000
        Log.d("인터벌액티비티", "nowecercisetime: $nowecercisetime")


        firstUI(setCount,nowisRunning )
        Log.d("인터벌액티비티", "초기데이터 setCount: ${setCount} exerciseCount:$exerciseCount")
        Log.d("인터벌액티비티", "초기데이터 range: ${nowProgram.program.intervalInfo!!.ranges!![exerciseCount-1]}")
//        adapter = CircleAdapter(nowProgram.program.intervalInfo?.ranges!!)
//        recyclerView.adapter = adapter
    }

    private fun setupUpdateReceiver() {

        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_INFO" -> {
                        val distance = intent.getDoubleExtra("distance", 0.0)
                        val speed = intent.getFloatExtra("speed", 0f)
                        val time = intent.getLongExtra("time", 0)
                        totalDistance = distance
                        totalTime = time

                        updatedisandspeedUI(distance,speed)
                    }
                    "com.example.sibal.UPDATE_TIMER" -> {
                        val time = intent.getLongExtra("time", 0)

                        realtime += 1000
                            updateTimerUI(realtime, nowecercisetime)
                        Log.d("Interval Activity", "UPDATE_TIMER: $time")
                        //이거는 모든 시간을 다 보여줌
                    }
                    "com.example.sibal.UPDATE_HEART_RATE" -> {
                        curHeartRate = intent.getFloatExtra("heartRate", 0f)
                        updateheartUI(curHeartRate)
                    }
                    "com.example.sibal.UPDATE_RANGE_INFO" -> {
                        // 세트 내 n 번째 구간
                        nowExerciseCount = intent.getIntExtra("rangeIndex", 0)
                        Log.d("Interval Activity", "UPDATE_RANGE_INFO: $nowExerciseCount")
                        //화면에 보여질 지금 운동구간임 +1한게 몇번째꺼인지 ㅇㅇ 화면에 보여주면됨
                        // 몇번째 세트
                        nowSetCount = intent.getIntExtra("setCount", 0) + 1
                        //+1을 해줌으로써 첫번째 세트라는걸 알려줌
                        Log.d("Interval Activity", "UPDATE_RANGE_INFO: $nowSetCount")
                        // 걷기, 달리기 여부
                        nowisRunning = intent.getBooleanExtra("isRunning", false)
                        Log.d("Interval Activity", "UPDATE_RANGE_INFO: $nowisRunning")
                        // 현재 구간 총 시간
                        nowecercisetime = intent.getLongExtra("rangeTime", 0)
                        Log.d("Interval Activity", "UPDATE_RANGE_INFO: $nowecercisetime")
                        //이게 다음세트꺼 시간을 의미하는건가 ?
                        realtime=0
                        updateDataUI(nowisRunning,nowSetCount)
                        adapter.updateIndex(nowExerciseCount)
                        vibrate(context)
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
            addAction("com.example.sibal.UPDATE_HEART_RATE")
            addAction("com.example.sibal.UPDATE_RANGE_INFO")
            addAction("com.example.sibal.EXIT_PROGRAM")
            addAction("com.example.siball.PAUSE_PROGRAM")
        }
        LocalBroadcastManager.getInstance(requireContext()).registerReceiver(updateReceiver, intentFilter)
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    private fun firstUI(setCount:Int, nowisRunning:Boolean){
        setView.text = "1 / $setCount"
        val resourceId = if (nowisRunning) {
            R.raw.run6  // 사용자가 달리기를 시작한 경우
        } else {
            R.raw.walk4 // 사용자가 달리기를 멈춘 경우
        }
        gifImageView.setImageResource(resourceId)
    }
    //심박수 UI업데이트
    private fun updateheartUI(heartRate: Float) {
        heartRateView.text = String.format("%d", heartRate.toInt())
    }
    //달린거리랑 속도 업데이트
    private fun updatedisandspeedUI(distance: Double,speed: Float) {
        distanceView.text = String.format("%.2f", distance / 1000)
        speedView.text = String.format("%.2f" , speed)
    }
    //타임업데이트
    private fun updateTimerUI(realtime: Long , nowecercisetime: Long) {
        var time = (nowecercisetime-realtime)
        val progress = 100 * (1 - (time.toFloat() / nowecercisetime))
        circleProgress.setProgress(progress)
        timeView.text = formatTime(time)
    }
    //시간이 있는것들만 보이게
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

    @SuppressLint("ResourceType")
    private fun updateDataUI(nowisRunning:Boolean, nowSetCount : Int){
        setView.text = "$nowSetCount / $setCount"
        val resourceId = if (nowisRunning) {
            R.raw.run8  // 사용자가 달리기를 시작한 경우
        } else {
            R.raw.walk8 // 사용자가 달리기를 멈춘 경우
        }
        gifImageView.setImageResource(resourceId)
    }

    private fun vibrate(context: Context) {
        val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(500)
        }
    }

    private fun sendResultsAndFinish(context: Context) {
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
        Log.d("인터벌", "sendResultsAndFinish: $intent")
        activity?.finish()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext()).unregisterReceiver(updateReceiver)
    }
}
