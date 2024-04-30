// TimerService.kt
package com.example.sibal.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.Binder
import java.util.Timer
import kotlin.concurrent.timer

class TimerService : Service() {

    private val binder = LocalBinder() // 서비스와 액티비티 간 바인딩을 위한 바인더
    private var timer: Timer? = null
    private var time = 0 // 타이머 상태를 저장

    inner class LocalBinder : Binder() {
        fun getService(): TimerService = this@TimerService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        timer = timer(period = 10) {
            time++ // 타이머 업데이트
        }
        return START_STICKY // 강제 종료 시에도 다시 시작
    }

    fun getTime(): Int {
        return time // 현재 타이머 값 반환
    }

    override fun onDestroy() {
        timer?.cancel() // 서비스 종료 시 타이머 중단
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder // 바인딩 서비스 반환
    }
}
