package com.example.gaenari.activity.runandwalk.walk

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
import com.example.gaenari.dto.request.Record
import com.example.gaenari.dto.request.SaveDataRequestDto
import com.example.gaenari.dto.request.Speeds
import com.example.gaenari.dto.response.FavoriteResponseDto
import com.example.gaenari.util.TTSUtil

import java.time.LocalDateTime


class WService : Service(), SensorEventListener {
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

    private var startTime: Long = 0

    private var oneMinuteDistance = 0.0
    private var elapsedTime: Long = 0

    private var timerTimeTargetMillis: Long = 1000

    // 일시정지 관련 변수
    private var totalPausedTime: Long = 0
    private var lastPauseTime: Long = 0

//    private var wakeLock: PowerManager.WakeLock? = null
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

                // 심박수 브로드캐스트
                sendHeartRateBroadcast(currentHeartRate)
            }
            timerHandler.postDelayed(this, timerTimeTargetMillis)
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

    private val notification: Notification
        get() {
            val notificationIntent = Intent(this, WalkingActivity::class.java)
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
//            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
//            wakeLock =
//                powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakeLockTag")
//            wakeLock?.acquire() // WakeLock 활성화
            try {
                Log.d("Check Walking Service", "Service started")
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
                Log.e("Check Walking Service", "Error in onCreate: ${e.message}")
            }
        }
    }

    /**
     * saveRequestDto 초기화
     */
    private fun initRequestDto() {
        Log.d("Check Walking Service", "ProgramData in InitRequestDto Method : $programData")
        val record = Record(0.0, 0.0)
        val speed = Speeds(0.0, ArrayList())
        val heartRate = HeartRates(0, ArrayList())

        requestDto = SaveDataRequestDto(
            date = LocalDateTime.now(),
            exerciseType = "W", programType = "DEFAULT", program = null,
            record = record, speeds = speed, heartrates = heartRate
        )

        Log.d("Check Walking Service", "Init RequestDto : $requestDto")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        programData = intent?.getParcelableExtra("programData", FavoriteResponseDto::class.java)

        initService()

        return super.onStartCommand(intent, flags, startId)
    }

    /**
     * 분 당 운동 기록 계산
     */
    private fun calculateOneMinuteAverages() {
        Log.d("Check Walking Service", "1분 당 평균 값 계산 시작")
        val averageSpeed = if (speedCount > 0) oneMinuteSpeed / speedCount else 0.0
        val averageHeartRate =
            if (heartRateCount > 0) (oneMinuteHeartRate / heartRateCount).toInt() else 0
        Log.d(
            "Check Walking Service",
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
     * 1분 이전 조기 종료 시 남은 정보 저장
     */
    private fun remainInfoSave(){
        Log.d("Check Walking Service", "조기 종료 시 평균 값 계산 시작")
        val averageSpeed = if (speedCount > 0) oneMinuteSpeed / speedCount else 0.0
        val averageHeartRate =
            if (heartRateCount > 0) (oneMinuteHeartRate / heartRateCount).toInt() else 0
        Log.d(
            "Check Walking Service",
            "RemainInfoAverage Info : $averageSpeed , $averageHeartRate ,$oneMinuteDistance"
        )

        /* Update requestDto */
        requestDto.speeds.average += averageSpeed
        requestDto.speeds.addSpeed(averageSpeed)
        requestDto.heartrates.average += averageHeartRate
        requestDto.heartrates.addHeartRate(averageHeartRate)

    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("Check Walking Service", "HeartRate onSensorChanged")
        if (!isPaused && event.sensor.type == Sensor.TYPE_HEART_RATE) {
            if(event.values[0] < 40) return
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
        requestDto.record.time = elapsedTime.toDouble()

//        wakeLock?.release()
        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        timerHandler.removeCallbacks(timerRunnable)
        oneMinuteHandler.removeCallbacks(oneMinuteRunnable)
        unregisterBroadcastReceiver()
        Log.d("Check Walking Service", "Walking Service destroyed")

        sendEndProgramBroadcast()
        super.onDestroy()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    inner class LocalBinder : Binder() {
        fun getService(): WService = this@WService
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
    private fun sendEndProgramBroadcast(){
        val intent = Intent("com.example.sibal.EXIT_PROGRAM").apply {
            putExtra("isEnd", true)
            putExtra("totalHeartRateAvg", requestDto.heartrates.average)
            putExtra("totalSpeedAvg", requestDto.speeds.average)
            putExtra("requestDto", requestDto)
        }
        Log.d("sendEndProgramBroadcast", "sendEndProgramBroadcast: ${requestDto} , ㅎㅇㅎㅇ1 ${requestDto.speeds.average} , ㅎㅇㅎㅇ2 ${requestDto.heartrates.average}")
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun pauseService() {
        lastPauseTime = SystemClock.elapsedRealtime()
        isPaused = true
        stopForeground(true)
        // 1분 평균 계산 핸들러를 중지합니다.
        oneMinuteHandler.removeCallbacks(oneMinuteRunnable)
        sendPauseBroadcast()
    }

    @SuppressLint("ForegroundServiceType")
    fun resumeService() {
        val currentResumeTime = SystemClock.elapsedRealtime()
        totalPausedTime += currentResumeTime - lastPauseTime
        isPaused = false
        isServiceRunning = true
        startForeground(1, notification)
        // 1분 평균 계산 핸들러를 다시 시작합니다.
        oneMinuteHandler.postDelayed(oneMinuteRunnable, 60000)
        sendPauseBroadcast()
    }

    /**
     * 일시정지 알림 브로드캐스트
     */
    private fun sendPauseBroadcast(){
        val intent = Intent("com.example.sibal.PAUSE_PROGRAM").apply {
            putExtra("isPause", isPaused)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    /**
     * Broadcast Receive 등록
     */
    private fun setupUpdateReceiver(){
        updateReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                when (intent.action) {
                    "com.example.sibal.UPDATE_LOCATION" -> {
                        if(!isPaused) {
                            val distance = intent.getDoubleExtra("distance", 0.0)
                            val speed = intent.getDoubleExtra("speed", 0.0)
                            Log.d("Check Walking Service", "Update Location : dist($distance), speed($speed)")
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
        LocalBroadcastManager.getInstance(this@WService)
            .registerReceiver(updateReceiver, intentFilter)
    }

    /**
     * Broadcast Receive 해제
     */
    private fun unregisterBroadcastReceiver() {
        Log.d("Check Walking Service", "Walking Service Unregister Broadcast Receiver")
        LocalBroadcastManager.getInstance(this).unregisterReceiver(updateReceiver)
    }
}
