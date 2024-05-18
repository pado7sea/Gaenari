package com.example.gaenari.activity.iactivity

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.*
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.dto.request.HeartRates
import com.example.gaenari.dto.request.IntervalInfo
import com.example.gaenari.dto.request.Program
import com.example.gaenari.dto.request.Ranges
import com.example.gaenari.dto.request.Record
import com.example.gaenari.dto.request.SaveDataRequestDto
import com.example.gaenari.dto.request.Speeds
import com.example.gaenari.dto.response.FavoriteResponseDto
import com.example.gaenari.util.TTSUtil
import java.time.LocalDateTime


class IntervalService : Service(), SensorEventListener {
    private lateinit var updateReceiver: BroadcastReceiver

    private val binder = LocalBinder()
    private var isServiceRunning = false
    private var sensorManager: SensorManager? = null
    private var heartRateSensor: Sensor? = null

    private var programData: FavoriteResponseDto? = null
    private lateinit var requestDto: SaveDataRequestDto

    private var totalDistance = 0.0
    private var currentHeartRate = 0f // 현재 심박수
    private var oneMinuteSpeed = 0.0
    private var oneMinuteHeartRate = 0f
    private var rangeSpeed = 0.0
    private var rangeSpeedCnt = 0
    private var speedCount = 0
    private var heartRateCount = 0

    private var currentSetCount = 0
    private var currentRangeIndex = 0
    private var currentRangeTime: Long = 60000
    private var currentRangeRemainTime: Long = 60000
    private var currentRunningType: Boolean = false
    private var currentRangeSpeed: Double = 0.0

    private var startTime: Long = 0

    private var oneMinuteDistance = 0.0
    private var elapsedTime: Long = 0

    private var timerIntervalMillis: Long = 1000

    // 일시정지 관련 변수
    private var totalPausedTime: Long = 0
    private var lastPauseTime: Long = 0

    private var isPaused = false

    /**
     * 타이머 핸들러를 통해 1초마다 시간을 갱신
     */
    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (!isPaused) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime - totalPausedTime
                sendTimeBroadcast(elapsedTime)
                currentRangeRemainTime -= 1000
                // 심박수 브로드캐스트
                sendHeartRateBroadcast(currentHeartRate)
            }
            timerHandler.postDelayed(this, timerIntervalMillis)
        }
    }

    /**
     * 1분 평균 계산 핸들러
     */
    private val oneMinuteHandler = Handler(Looper.getMainLooper())
    private val oneMinuteRunnable = object : Runnable {
        override fun run() {
            calculateOneMinuteAverages()
            oneMinuteHandler.postDelayed(this, 60000) // 1분 간격 실행
        }
    }

    /**
     * 구간 평균 계산 핸들러
     */
    private val rangeHandler = Handler(Looper.getMainLooper())
    private val rangeRunnable = object : Runnable {
        override fun run() {
            Log.d("Delay", "Range Delay Time Check : $currentRangeTime")
            calculateRangeAverages()
            rangeHandler.postDelayed(this, currentRangeTime)
        }
    }

    private val notification: Notification
        get() {
            val notificationIntent = Intent(this, IActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            return NotificationCompat.Builder(this, "ForegroundServiceChannel")
                .setContentTitle("Exercise Running")
                .setContentText("Tracking your exercise")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentIntent(pendingIntent)
                .build()
        }

    @SuppressLint("ForegroundServiceType")
    private fun initService() {
        startTime = SystemClock.elapsedRealtime()
        if (!isServiceRunning) {
            isServiceRunning = true
            try {
                Log.d("Check Interval Service", "Service started")

                TTSUtil.speak("멍!멍!! 운동을  시작한다!")

                createNotificationChannel()
                startForeground(1, notification)
                setupHeartRateSensor()
                setupUpdateReceiver()
                initRequestDto()
                startTime = SystemClock.elapsedRealtime()
                timerHandler.postDelayed(timerRunnable, 1000) // 타이머 시작
                oneMinuteHandler.postDelayed(oneMinuteRunnable, 60000) // 1분 평균 계산 타이머 시작
            } catch (e: Exception) {
                Log.e("Check Interval Service", "Error in onCreate: ${e.message}")
            }
        }
    }

    /**
     * saveRequestDto 초기화
     */
    private fun initRequestDto() {
        Log.d("Check Interval Service", "initRequestDto() 들어옴")
        Log.d("Check Interval Service", "ProgramData in InitRequestDto Method : $programData")
        val intervalInfo = IntervalInfo(ArrayList())
        val program = Program(programData?.programId!!, intervalInfo)
        val record = Record(0.0, 0.0)
        val speed = Speeds(0.0, ArrayList())
        val heartRate = HeartRates(0, ArrayList())

        requestDto = SaveDataRequestDto(
            date = LocalDateTime.now(),
            exerciseType = "P", programType = "I", program = program,
            record = record, speeds = speed, heartrates = heartRate
        )

        Log.d("Check Interval Service", "Init RequestDto : $requestDto")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        programData = intent?.getParcelableExtra("programData", FavoriteResponseDto::class.java)
        initService()
        updateNextRangeInfo(false)

        rangeHandler.postDelayed(rangeRunnable, currentRangeTime)

        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 분 당 운동 기록 계산
     */
    private fun calculateOneMinuteAverages() {
        Log.d("Check Interval Service", "1분 당 평균 값 계산 시작")
        val averageSpeed = if (speedCount > 0) oneMinuteSpeed / speedCount else 0.0
        val averageHeartRate =
            if (heartRateCount > 0) (oneMinuteHeartRate / heartRateCount).toInt() else 0
        Log.d(
            "Check Interval Service",
            "OneMinuteAverage Info : $averageSpeed , $averageHeartRate ,$oneMinuteDistance"
        )

        /* Update requestDto */
        requestDto.speeds.average += averageSpeed
        requestDto.speeds.addSpeed(averageSpeed)
        requestDto.heartrates.average += averageHeartRate
        requestDto.heartrates.addHeartRate(averageHeartRate)

        /* 다음 1분을 위한 초기화 */
        oneMinuteSpeed = 0.0
        speedCount = 0
        oneMinuteHeartRate = 0f
        heartRateCount = 0
        oneMinuteDistance = 0.0
    }

    /**
     * 구간 운동 기록 계산
     */
    private fun calculateRangeAverages() {
        val isRunning = currentRunningType
        val rangeTime = currentRangeTime
        val averageSpeed = if (rangeSpeedCnt > 0) rangeSpeed / rangeSpeedCnt else 0.0
        Log.d(
            "Check Interval Service",
            "Calculate Range Average : $isRunning, ${rangeTime}, $averageSpeed"
        )

        /* Update requestDto Range Info */
        val range = Ranges(
            isRunning = isRunning,
            time = (rangeTime / 1000).toInt(),
            speed = averageSpeed
        )

        Log.d("Check Interval Service", "Input Range Info to RequestDto : $range")
        requestDto.program!!.intervalInfo?.addRange(range)
        Log.d("Check Interval Service", "RequestDto Status : $requestDto")

        // 속도 정보 초기화
        rangeSpeed = 0.0
        rangeSpeedCnt = 0

        isEndOfProgram()
        updateNextRangeInfo(true)
    }

    /**
     * 인터벌 프로그램 종료조건 확인
     */
    private fun isEndOfProgram() {
        /* 모든 세트의 완료 여부 확인 */
        if (currentSetCount == programData?.program?.intervalInfo?.setCount) {
            Log.d("Check Interval Service", "Interval Service Stop")
            onDestroy()
        }
    }

    /**
     * 다음 인터벌 구간 정보 Update
     */
    private fun updateNextRangeInfo(flag: Boolean) {
        Log.d("Check Interval Service", "Next Range Info Index : $currentRangeIndex")

        /* 다음 구간 정보 */
        currentRangeTime =
            programData?.program?.intervalInfo?.ranges?.get(currentRangeIndex)?.time?.toLong()!! * 1000
        currentRunningType =
            programData?.program?.intervalInfo?.ranges?.get(currentRangeIndex)?.isRunning!!
        currentRangeSpeed =
            programData?.program?.intervalInfo?.ranges?.get(currentRangeIndex)?.speed!!

        currentRangeRemainTime = currentRangeTime

        if (flag)
            sendRangeInfoBroadcast()

        currentRangeIndex += 1

        Log.d(
            "Check Interval Service",
            "Range Count : current($currentRangeIndex), total(${programData?.program?.intervalInfo?.rangeCount})"
        )

        /* 세트 종료 시 구간 정보 초기화 */
        if (currentRangeIndex == programData?.program?.intervalInfo?.rangeCount) {
            currentRangeIndex = 0
            currentSetCount++
        }

        Log.d(
            "Check Interval Service",
            "Set Count : current($currentSetCount), total(${programData?.program?.intervalInfo?.setCount})"
        )
    }

    /**
     * 구간 정보 브로드 캐스트
     */
    private fun sendRangeInfoBroadcast() {
        val intent = Intent("com.example.sibal.UPDATE_RANGE_INFO").apply {
            putExtra("rangeIndex", currentRangeIndex)
            putExtra("setCount", currentSetCount)
            putExtra("isRunning", currentRunningType)
            putExtra("rangeTime", currentRangeTime)
            putExtra("rangeSpeed", currentRangeSpeed)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * 1분 이전 조기 종료 시 남은 정보 저장
     */
    private fun remainInfoSave() {
        Log.d("Check Interval Service", "조기 종료 시 평균 값 계산 시작")
        val averageSpeed = if (speedCount > 0) oneMinuteSpeed / speedCount else 0.0
        val averageHeartRate =
            if (heartRateCount > 0) (oneMinuteHeartRate / heartRateCount).toInt() else 0
        Log.d(
            "Check Interval Service",
            "RemainInfoAverage Info : $averageSpeed , $averageHeartRate ,$oneMinuteDistance"
        )

        /* Update requestDto */
        requestDto.speeds.average += averageSpeed
        requestDto.speeds.addSpeed(averageSpeed)
        requestDto.heartrates.average += averageHeartRate
        requestDto.heartrates.addHeartRate(averageHeartRate)

    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("Check Interval Service", "HeartRate onSensorChanged")
        if (!isPaused && event.sensor.type == Sensor.TYPE_HEART_RATE) {
            if (event.values[0] < 40) return
            currentHeartRate = event.values[0]
            oneMinuteHeartRate += currentHeartRate
            heartRateCount++
        }
    }

    override fun onDestroy() {
        remainInfoSave()

        /* 분 당 정보 누적합을 누적 개수로 나누어 전체 평균 계산 */
        requestDto.speeds.average /= requestDto.speeds.arr.size
        requestDto.heartrates.average /= requestDto.heartrates.arr.size

        /* record 정보 추가 */
        requestDto.record.distance = totalDistance
        requestDto.record.time = (elapsedTime / 1000).toDouble()

        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        timerHandler.removeCallbacks(timerRunnable)
        oneMinuteHandler.removeCallbacks(oneMinuteRunnable)
        rangeHandler.removeCallbacks(rangeRunnable)
        unregisterBroadcastReceiver()
        Log.d("IRunningService", "Service destroyed")
        sendEndProgramBroadcast()
        super.onDestroy()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    inner class LocalBinder : Binder() {
        fun getService(): IntervalService = this@IntervalService
    }

    override fun onBind(intent: Intent): IBinder = binder

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            "ForegroundServiceChannel",
            "Exercise Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }

    private fun setupHeartRateSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        heartRateSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    /**
     * GPS 브로드캐스트 통해 거리, 시간, 속도 전송
     */
    private fun sendGpsBroadcast(distance: Double, time: Long, speed: Float) {
        val intent = Intent("com.example.sibal.UPDATE_INFO").apply {
            putExtra("distance", distance)
            putExtra("time", time)
            putExtra("speed", speed)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * 타이머 정보를 브로드캐스트
     */
    private fun sendTimeBroadcast(time: Long) {
        val intent = Intent("com.example.sibal.UPDATE_TIMER").apply {
            putExtra("time", time)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * 심박수를 브로드캐스트하는 함수
     */
    private fun sendHeartRateBroadcast(currentHeartRate: Float) {
        val intent = Intent("com.example.sibal.UPDATE_HEART_RATE").apply {
            putExtra("heartRate", currentHeartRate)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * 프로그램 종료 브로드 캐스트
     */
    private fun sendEndProgramBroadcast() {
        val intent = Intent("com.example.sibal.EXIT_PROGRAM").apply {
            putExtra("isEnd", true)
            putExtra("totalHeartRateAvg", requestDto.heartrates.average)
            putExtra("totalSpeedAvg", requestDto.speeds.average)
            putExtra("requestDto", requestDto)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun pauseService() {
        lastPauseTime = SystemClock.elapsedRealtime()
        isPaused = true
        stopForeground(true)
        // 1분 평균 계산 핸들러를 중지합니다.
        oneMinuteHandler.removeCallbacks(oneMinuteRunnable)
        rangeHandler.removeCallbacks(rangeRunnable)
        sendPauseBroadcast()
    }

    @SuppressLint("ForegroundServiceType")
    fun resumeService() {
        val currentResumeTime = SystemClock.elapsedRealtime()
        totalPausedTime += currentResumeTime - lastPauseTime
        isPaused = false
        isServiceRunning = true
        startForeground(1, notification)
        Log.d("Check Interval Service", "oneMinuteHandler Delayed ${60000 - elapsedTime % 60000}")
        oneMinuteHandler.postDelayed(oneMinuteRunnable, 60000 - elapsedTime % 60000)
        Log.d("Check Interval Service", "rangeHandler Delayed $currentRangeRemainTime")
        rangeHandler.postDelayed(rangeRunnable, currentRangeRemainTime)
        sendPauseBroadcast()
    }

    /**
     * 일시정지 알림 브로드캐스트
     */
    private fun sendPauseBroadcast() {
        val intent = Intent("com.example.sibal.PAUSE_PROGRAM").apply {
            putExtra("isPause", isPaused)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * Broadcast Receive 등록
     */
    private fun setupUpdateReceiver() {
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_LOCATION" -> {
                        if (!isPaused) {
                            val distance = intent.getDoubleExtra("distance", 0.0)
                            val speed = intent.getDoubleExtra("speed", 0.0)
                            Log.d(
                                "Check Interval Service",
                                "Update Location : dist($distance), speed($speed)"
                            )
                            // 분 당 속도
                            oneMinuteSpeed += speed
                            speedCount++

                            // 구간 당 속도
                            rangeSpeed += speed
                            rangeSpeedCnt++

                            totalDistance += distance
                            oneMinuteDistance += distance

                            sendGpsBroadcast(
                                totalDistance,
                                elapsedTime,
                                speed.toFloat()
                            )
                        }
                    }
                }
            }
        }

        val intentFilter = IntentFilter().apply {
            addAction("com.example.sibal.UPDATE_LOCATION")
        }
        LocalBroadcastManager.getInstance(this@IntervalService)
            .registerReceiver(updateReceiver, intentFilter)
    }

    /**
     * Broadcast Receive 해제
     */
    private fun unregisterBroadcastReceiver() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver)
    }
}
