// TimerService.kt
package com.example.gaenari.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Binder
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.gaenari.activity.dactivity.DsendData
import com.example.gaenari.data.DRunningViewModel
import java.util.Timer
import kotlin.concurrent.timer

class TimerService : Service() {

    private lateinit var viewModel: DRunningViewModel

    private var timer: Timer? = null
    private var time = 0
    private var isPaused = false

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onCreate() {
        super.onCreate()
        viewModel = ViewModelProvider(applicationContext as ViewModelStoreOwner).get(DRunningViewModel::class.java)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer = timer(period = 1000) {
            if (!isPaused) {
                time++
                viewModel.setRecord(DsendData.Record(distance = 0.0, time = time.toLong()))
            }
        }
        return START_STICKY
    }

    fun pauseTimer() {
        isPaused = true
    }

    fun resumeTimer() {
        isPaused = false
    }

    fun getTime(): Int {
        return time
    }

    override fun onDestroy() {
        timer?.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }
}
