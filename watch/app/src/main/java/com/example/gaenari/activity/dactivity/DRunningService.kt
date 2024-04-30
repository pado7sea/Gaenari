
package com.example.gaenari.activity.dactivity

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

class DRunningService : Service(), SensorEventListener {
    private val binder = LocalBinder()
    private var isServiceRunning = false
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var sensorManager: SensorManager? = null
    private var heartRateSensor: Sensor? = null
    private var totalDistance = 0.0
    private var lastLocation: Location? = null
    private var startTime: Long = 0
    private var heartRate = 0f // Current heart rate
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            locationResult ?: return
            try {
                locationResult.locations.forEach { location ->
                    lastLocation?.let {
                        totalDistance += it.distanceTo(location).toDouble()
//                        val speed = it.distanceTo(location) * 1000 / (location.time - it.time)
                        val intent = Intent("com.example.sibal.UPDATE_INFO").apply {
                            putExtra("distance", totalDistance)
                            putExtra("time", SystemClock.elapsedRealtime() - startTime)
                            putExtra("heartRate", heartRate)
                            putExtra("speed", location.speed)
                        }
                        Log.d("DRunningService", "Sending broadcast: Distance $totalDistance, Time ${SystemClock.elapsedRealtime() - startTime}")
                        LocalBroadcastManager.getInstance(this@DRunningService).sendBroadcast(intent)
                    }
                    lastLocation = location
                }
            } catch (e: Exception) {
                Log.e("DRunningService", "Error processing location update: ${e.message}")
            }
        }
    }

    inner class LocalBinder : Binder() {
        fun getService(): DRunningService = this@DRunningService
    }

    @SuppressLint("ForegroundServiceType")
    override fun onCreate() {
        super.onCreate()
        if (!isServiceRunning) {
            isServiceRunning = true
            try {
                Log.d("DRunningService", "Service started")
                startTime = SystemClock.elapsedRealtime()
                createNotificationChannel()
                startForeground(1, notification)
                setupLocationTracking()
                setupHeartRateSensor()
            } catch (e: Exception) {
                Log.e("DRunningService", "Error in onCreate: ${e.message}")
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent == null) {
            Log.d("DRunningService", "Restarted by the system.")
        } else {
            if (!isServiceRunning) {
                isServiceRunning = true
                Log.d("DRunningService", "Service starting")
                // 서비스 초기 설정 코드
            } else {
                Log.d("DRunningService", "Service already running")
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        isServiceRunning = false
        sensorManager?.unregisterListener(this)
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        Log.d("DRunningService", "Service destroyed")
    }

    fun stopService() {
        stopSelf()
    }

    private val notification: Notification
        get() {
            val notificationIntent = Intent(this, DActivity::class.java)
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
            Log.e("DRunningService", "No heart rate sensor available")
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
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
        // 위치 및 센서 업데이트 일시정지
        fusedLocationClient?.removeLocationUpdates(locationCallback)
        sensorManager?.unregisterListener(this)
        stopForeground(true) // 포그라운드 서비스 중지
        isServiceRunning = false // 서비스 실행 중지
    }

    fun resumeService() {
        // 위치 및 센서 업데이트 재개
        setupLocationTracking()
        setupHeartRateSensor()
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
            Log.e("DRunningService", "Error creating notification channel: ${e.message}")
        }
    }
}