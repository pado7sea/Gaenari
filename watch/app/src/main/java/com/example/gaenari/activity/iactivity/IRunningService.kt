
package com.example.gaenari.activity.iactivity

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

class IRunningService : Service(), SensorEventListener {
    private val binder = LocalBinder()
    private var isServiceRunning = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var sensorManager: SensorManager? = null
    private var heartRateSensor: Sensor? = null

    private var lastLocation: Location? = null

    private var totalDistance = 0.0
    private var currentHeartRate = 0f // Current heart rate
    private var oneMinuteSpeed = 0.0
    private var oneMinuteHeartRate = 0f
    private var speedCount = 0
    private var heartRateCount = 0

    private var startTime: Long = 0

    private var oneMinuteDistance = 0.0
    private var elapsedTime: Long = 0

    private var gpsUpdateIntervalMillis: Long = 2500
    private var timerIntervalMillis: Long = 1000

    // 일시정지에 대한 처리를 해주기위함
    private var totalPausedTime: Long = 0
    private var lastPauseTime: Long = 0

    private var wakeLock: PowerManager.WakeLock? = null

    //일시정지일때 데이터를 안보내는 방식을 택함
    private var isPaused = false

//    private val timerHandler = Handler(Looper.getMainLooper())
//    private val timerRunnable = object : Runnable {
//        override fun run() {
//            if (!isPaused) {
//                elapsedTime = SystemClock.elapsedRealtime() - startTime - totalPausedTime
//                sendTimeBroadcast(elapsedTime)
//                sendHeartRateBroadcast(currentHeartRate)
//            }
//            timerHandler.postDelayed(this, timerIntervalMillis)
//        }
//    }
//
//    private val oneMinuteHandler = Handler(Looper.getMainLooper())
//    private val oneMinuteRunnable = object : Runnable {
//        override fun run() {
//            calculateOneMinuteAverages()
//            oneMinuteHandler.postDelayed(this, 60000) // 1분 간격 실행
//        }
//    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        if (!isServiceRunning) {
            isServiceRunning = true
            val powerManager = getSystemService(POWER_SERVICE) as PowerManager
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakeLockTag")
            wakeLock?.acquire()
            try {
                Log.d("IRunningService", "Service started")
                startTime = SystemClock.elapsedRealtime()
                createNotificationChannel()
                startForeground(1, notification)
                setupLocationTracking()
                setupHeartRateSensor()
            } catch (e: Exception) {
                Log.e("IRunningService", "Error in onCreate: ${e.message}")
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            if (isPaused) return
            val currentTime = SystemClock.elapsedRealtime()
            val activeTime = currentTime - startTime - totalPausedTime  // 실제 활동 시간 계산

            if (activeTime < 4555) {  // 첫 5초 동안의 위치 업데이트는 무시
                return
            }
            try {
                locationResult.locations.forEach { location ->
                    lastLocation?.let {
                        totalDistance += it.distanceTo(location).toDouble()
//                        val speed = it.distanceTo(location) * 1000 / (location.time - it.time)
                        val intent = Intent("com.example.sibal.UPDATE_INFO").apply {
                            putExtra("distance", totalDistance)
                            putExtra("time", activeTime)
                            putExtra("heartRate", currentHeartRate)
                            putExtra("speed", location.speed)
                        }
                        Log.d("IRunningService", "Sending broadcast: Distance $totalDistance, Time ${SystemClock.elapsedRealtime() - startTime}")
                        LocalBroadcastManager.getInstance(this@IRunningService).sendBroadcast(intent)
                    }
                    lastLocation = location
                }
            } catch (e: Exception) {
                Log.e("IRunningService", "Error processing location update: ${e.message}")
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): IRunningService = this@IRunningService
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            Log.d("IRunningService", "Restarted by the system.")
        } else {
            if (!isServiceRunning) {
                isServiceRunning = true
                Log.d("IRunningService", "Service starting")
                // 서비스 초기 설정 코드
            } else {
                Log.d("IRunningService", "Service already running")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        Log.d("IRunningService", "Service destroyed")
    }

    fun stopService() {
        stopSelf()
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

    private fun setupLocationTracking() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val locationRequest = LocationRequest.create().apply {
            interval = 1000 // 1 second interval
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    private fun setupHeartRateSensor() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        heartRateSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        heartRateSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: run {
            Log.e("IRunningService", "No heart rate sensor available")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
            currentHeartRate = event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // 필요에 따라 구현
    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }

    fun pauseService() {
        lastPauseTime = SystemClock.elapsedRealtime()  // 일시 정지 시작 시간 기록
        isPaused = true
        stopForeground(true)
    }

    @SuppressLint("ForegroundServiceType")
    fun resumeService() {
        val currentResumeTime = SystemClock.elapsedRealtime()
        totalPausedTime += currentResumeTime - lastPauseTime  // 누적 일시 정지 시간 갱신
        isPaused = false
        isServiceRunning = true
        startForeground(1, notification)
    }

    private fun createNotificationChannel() {
        try {
            val serviceChannel = NotificationChannel(
                "ForegroundServiceChannel",
                "Exercise Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        } catch (e: Exception) {
            Log.e("IRunningService", "Error creating notification channel: ${e.message}")
        }
    }
}