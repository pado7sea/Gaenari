package com.example.gaenari.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Binder
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.gaenari.activity.main.HomeActivity
import com.example.gaenari.activity.tactivity.TActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority

class LocationService : Service() {
    private val binder = LocalBinder()
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var lastLocation: Location? = null

    inner class LocalBinder : Binder() {
        fun getService(): LocationService = this@LocationService
    }

    override fun onBind(intent: Intent): IBinder = binder

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(2, notification) // 포그라운드 서비스 시작
        setupLocationTracking(applicationContext)
        return super.onStartCommand(intent, flags, startId)
    }

    private fun setupLocationTracking(context: Context) {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        val locationRequest =
            LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000)
                .build()

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient?.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            try {
                locationResult.locations.forEach { location ->
                    val speed = location.speed.toDouble() * 3.6
                    val distance = location.distanceTo(lastLocation ?: location).toDouble()
                    Log.d("Check", "Sending Location Info : speed($speed), distance($distance)")
                    sendLocationBroadcast(distance, speed)
                    lastLocation = location
                }
            } catch (e: Exception) {
                Log.e("Check", "Error processing location update: ${e.message}")
            }
        }
    }

    /**
     * GPS 브로드캐스트 통해 거리, 속도 전송
     */
    private fun sendLocationBroadcast(distance: Double, speed: Double) {
        val intent = Intent("com.example.sibal.UPDATE_LOCATION").apply {
            putExtra("distance", distance)
            putExtra("speed", speed)
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }

    private val notification: Notification
        get() {
            val notificationIntent = Intent(this, HomeActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(
                this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            return NotificationCompat.Builder(this, "LocationServiceChannel")
                .setContentTitle("Location Running")
                .setContentText("Tracking your exercise")
                .setContentIntent(pendingIntent)
                .build()
        }

    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            "LocationServiceChannel",
            "Location Service Channel",
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(serviceChannel)
    }
}