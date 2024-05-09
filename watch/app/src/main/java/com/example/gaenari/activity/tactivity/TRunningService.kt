package com.example.gaenari.activity.tactivity

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.*
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.*
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TRunningService : Service(), SensorEventListener {
    private val binder = LocalBinder()
    private var isServiceRunning = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var sensorManager: SensorManager? = null
    private var heartRateSensor: Sensor? = null

    private var lastLocation: Location? = null

    private var totalDistance = 0.0
    private var currentHeartRate = 0f // 현재 심박수
    private var accumulatedSpeed = 0.0
    private var accumulatedHeartRate = 0f
    private var speedCount = 0
    private var heartRateCount = 0

    private var startTime: Long = 0

    private var oneMinuteDistance = 0.0
    private var elapsedTime: Long = 0

    private var gpsUpdateIntervalMillis: Long = 2500
    private var timerIntervalMillis: Long = 1000

    // 일시정지 관련 변수
    private var totalPausedTime: Long = 0
    private var lastPauseTime: Long = 0

    private var wakeLock: PowerManager.WakeLock? = null
    private var isPaused = false

    // 타이머 핸들러를 통해 1초마다 시간을 갱신
    private val timerHandler = Handler(Looper.getMainLooper())
    private val timerRunnable = object : Runnable {
        override fun run() {
            if (!isPaused) {
                elapsedTime = SystemClock.elapsedRealtime() - startTime - totalPausedTime
                sendTimeBroadcast(elapsedTime)

                // 심박수 브로드캐스트
                sendHeartRateBroadcast(currentHeartRate)
            }
            timerHandler.postDelayed(this, timerIntervalMillis)
        }
    }

    // 1분 평균 계산 핸들러
    private val oneMinuteHandler = Handler(Looper.getMainLooper())
    private val oneMinuteRunnable = object : Runnable {
        override fun run() {
            calculateOneMinuteAverages()
            oneMinuteHandler.postDelayed(this, 60000) // 1분 간격 실행
        }
    }
    private val notification: Notification
        get() {
            val notificationIntent = Intent(this, TActivity::class.java)
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
    override fun onCreate() {
        super.onCreate()
        startTime = SystemClock.elapsedRealtime()
        if (!isServiceRunning) {
            isServiceRunning = true
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakeLockTag")
            wakeLock?.acquire() // WakeLock 활성화
            try {
                Log.d("TRunningService", "Service started")
                startTime = SystemClock.elapsedRealtime()
                createNotificationChannel()
                startForeground(1, notification)
                setupLocationTracking()
                setupHeartRateSensor()
                timerHandler.post(timerRunnable) // 타이머 시작
                oneMinuteHandler.post(oneMinuteRunnable) // 1분 평균 계산 타이머 시작
            } catch (e: Exception) {
                Log.e("TRunningService", "Error in onCreate: ${e.message}")
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (isPaused) return
            try {
                locationResult.locations.forEach { location ->
                    val speed = location.speed.toDouble()
                    accumulatedSpeed += speed
                    speedCount++

                    val distance = location.distanceTo(lastLocation ?: location).toDouble()
                    totalDistance += distance
                    oneMinuteDistance += distance
                    sendGpsBroadcast(totalDistance, elapsedTime, speed.toFloat())
                    lastLocation = location
                }
            } catch (e: Exception) {
                Log.e("TRunningService", "Error processing location update: ${e.message}")
            }
        }
    }

    private fun calculateOneMinuteAverages() {
        Log.d("checkcheck", "시작은 하니 ?: ")
        val averageSpeed = if (speedCount > 0) accumulatedSpeed / speedCount else 0.0
        val averageHeartRate = if (heartRateCount > 0) (accumulatedHeartRate / heartRateCount).toInt() else 0
        Log.d("checkcheck", "서비스다${averageSpeed} , ${averageHeartRate} ,${oneMinuteDistance}")
        // 1분 데이터 브로드캐스트
        val intent = Intent("com.example.sibal.UPDATE_ONE_MINUTE").apply {
            putExtra("averageSpeed", averageSpeed)
            putExtra("averageHeartRate", averageHeartRate)
            putExtra("distance", oneMinuteDistance)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

        // 다음 1분을 위한 초기화
        accumulatedSpeed = 0.0
        speedCount = 0
        accumulatedHeartRate = 0f
        heartRateCount = 0
        oneMinuteDistance = 0.0
    }

    override fun onSensorChanged(event: SensorEvent) {
        Log.d("checkcheck", "onSensorChanged: 님아됨 ? ㅋㅋㅋㅋ ")
        if (!isPaused && event.sensor.type == Sensor.TYPE_HEART_RATE) {
            currentHeartRate = event.values[0]
            accumulatedHeartRate += currentHeartRate
            heartRateCount++
        }
    }

    override fun onDestroy() {
        wakeLock?.release()
        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        timerHandler.removeCallbacks(timerRunnable)
        oneMinuteHandler.removeCallbacks(oneMinuteRunnable)
        Log.d("TRunningService", "Service destroyed")
        super.onDestroy()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}

    inner class LocalBinder : Binder() {
        fun getService(): TRunningService = this@TRunningService
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

    private fun setupLocationTracking() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, gpsUpdateIntervalMillis)
            .build()

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun setupHeartRateSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        heartRateSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    // GPS 브로드캐스트를 통해 거리, 시간, 속도를 보내는 함수
    private fun sendGpsBroadcast(distance: Double, time: Long, speed: Float) {
        val intent = Intent("com.example.sibal.UPDATE_INFO").apply {
            putExtra("distance", distance)
            putExtra("time", time)
            putExtra("speed", speed)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    // 타이머 정보를 브로드캐스트하는 함수
    private fun sendTimeBroadcast(time: Long) {
        val intent = Intent("com.example.sibal.UPDATE_TIMER").apply {
            putExtra("time", time)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    // 심박수를 브로드캐스트하는 함수
    private fun sendHeartRateBroadcast(currentHeartRate: Float) {
        val intent = Intent("com.example.sibal.UPDATE_HEART_RATE").apply {
            putExtra("heartRate", currentHeartRate)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    fun pauseService() {
        lastPauseTime = SystemClock.elapsedRealtime()
        isPaused = true
        stopForeground(true)
    }

    @SuppressLint("ForegroundServiceType")
    fun resumeService() {
        val currentResumeTime = SystemClock.elapsedRealtime()
        totalPausedTime += currentResumeTime - lastPauseTime
        isPaused = false
        isServiceRunning = true
        startForeground(1, notification)
    }

    fun stopService() {
        stopSelf()
    }
}
