package com.example.gaenari.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.location.Location
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.location.*

class LocationService : Service() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private val locationData = MutableLiveData<Location>()
    private val distanceData = MutableLiveData<Double>()
    private var lastLocation: Location? = null
    private var totalDistance = 0.0

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            val newLocation = locationResult.lastLocation ?: return

            // 속도 (m/s)
            val speed = newLocation.speed
            // 총 이동 거리 계산
            if (lastLocation != null) {
                val distance = lastLocation!!.distanceTo(newLocation).toDouble()
                totalDistance += distance
                distanceData.postValue(totalDistance)
            }

            lastLocation = newLocation
            locationData.postValue(newLocation)
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 20
        private const val CHANNEL_ID = "LocationServiceChannel"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Location Tracking Service",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel)
        }
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_map)
            .setContentTitle("Location Service Running")
            .setContentText("Tracking location data")
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, getNotification())
        startTracking()
        return START_STICKY
    }

    private fun startTracking() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(1000)
            .setMaxUpdateDelayMillis(1000)
            .build()

        try {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null // 기본 메인 스레드에서 호출
            )
        } catch (e: SecurityException) {
            // 위치 권한이 없는 경우
        }
    }

    private fun stopTracking() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onDestroy() {
        stopTracking()
        stopForeground(true)
        super.onDestroy()
    }

    fun getLocationLiveData(): MutableLiveData<Location> = locationData
    fun getDistanceLiveData(): MutableLiveData<Double> = distanceData
}
