package com.example.gaenari.activity.tactivity

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.PowerManager
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class TRunningService : Service(), SensorEventListener {
    private val binder = LocalBinder()
    private var isServiceRunning = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var sensorManager: SensorManager? = null
    private var heartRateSensor: Sensor? = null
    private var totalDistance = 0.0
    private var lastLocation: Location? = null
    private var startTime: Long = 0
    private var heartRate = 0f // Current heart rate

    // 일시정지에 대한 처리를 해주기위함
    private var totalPausedTime: Long = 0
    private var lastPauseTime: Long = 0

    private var wakeLock: PowerManager.WakeLock? = null
    //일시정지일때 데이터를 안보내는 방식을 택함
    private var isPaused = false

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
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
            } catch (e: Exception) {
                Log.e("TRunningService", "Error in onCreate: ${e.message}")
            }
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
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
                            putExtra("heartRate", heartRate)
                            putExtra("speed", location.speed)
                        }
                        Log.d("TRunningService", "Sending broadcast: Distance $totalDistance, Time ${SystemClock.elapsedRealtime() - startTime}")
                        LocalBroadcastManager.getInstance(this@TRunningService).sendBroadcast(intent)
                    }
                    lastLocation = location
                }
            } catch (e: Exception) {
                Log.e("TRunningService", "Error processing location update: ${e.message}")
            }
        }
    }
    inner class LocalBinder : Binder() {
        fun getService(): TRunningService = this@TRunningService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            Log.d("TRunningService", "Restarted by the system.")
        } else {
            if (!isServiceRunning) {
                isServiceRunning = true
                Log.d("TRunningService", "Service starting")
                // 서비스 초기 설정 코드
            } else {
                Log.d("TRunningService", "Service already running")
            }
        }
        return START_STICKY
    }
    override fun onDestroy() {
        super.onDestroy()
        wakeLock?.release()
        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        Log.d("TRunningService", "Service destroyed")
    }

    fun stopService() {
        stopSelf()
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
            Log.e("TRunningService", "No heart rate sensor available")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (!isPaused && event.sensor.type == Sensor.TYPE_HEART_RATE) {
            heartRate = event.values[0]
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
            Log.e("TRunningService", "Error creating notification channel: ${e.message}")
        }
    }
}