package com.example.gaenari.service

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData

class SensorService : Service() {
    private lateinit var sensorManager: SensorManager
    private val heartRateLiveData = MutableLiveData<Int>()
    private val heartRateSensorListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
                val heartRate = event.values[0].toInt()
                heartRateLiveData.postValue(heartRate)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
            // 필요에 따라 정확도 변경에 대한 로직 추가 가능
        }
    }

    companion object {
        private const val NOTIFICATION_ID = 10
        private const val CHANNEL_ID = "HeartRateService"
        private const val CHANNEL_NAME = "Heart Rate Monitoring"
        private const val NOTIFICATION_TITLE = "Heart Rate Monitoring Service"
        private const val NOTIFICATION_TEXT = "Monitoring your heart rate"
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun getNotification(): Notification {
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(NOTIFICATION_TITLE)
            .setContentText(NOTIFICATION_TEXT)
            .build()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @SuppressLint("ForegroundServiceType")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, getNotification())
        registerHeartRateSensor()
        return START_STICKY
    }

    private fun registerHeartRateSensor() {
        val heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)
        if (heartRateSensor != null) {
            sensorManager.registerListener(
                heartRateSensorListener,
                heartRateSensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
    }

    private fun unregisterSensors() {
        sensorManager.unregisterListener(heartRateSensorListener)
    }

    override fun onDestroy() {
        unregisterSensors()
        stopForeground(true)
        super.onDestroy()
    }

    fun getHeartRateLiveData(): MutableLiveData<Int> = heartRateLiveData
}
