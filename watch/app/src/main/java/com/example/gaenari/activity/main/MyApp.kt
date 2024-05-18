package com.example.gaenari.activity.main

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.example.gaenari.service.LocationService
import com.example.gaenari.util.TTSUtil

class MyApp : Application() {

    private var activityReferences = 0
    private var isActivityChangingConfigurations = false

    override fun onCreate() {
        super.onCreate()

        setUpApp()

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {}

            override fun onActivityStarted(activity: Activity) {
                if (++activityReferences == 1 && !isActivityChangingConfigurations) {
                    // 앱이 포그라운드로 전환됨
                }
            }

            override fun onActivityResumed(activity: Activity) {}

            override fun onActivityPaused(activity: Activity) {}

            override fun onActivityStopped(activity: Activity) {
                isActivityChangingConfigurations = activity.isChangingConfigurations
                if (--activityReferences == 0 && !isActivityChangingConfigurations) {
                    // 앱이 백그라운드로 전환됨
                    onAppBackgrounded()
                }
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}

            override fun onActivityDestroyed(activity: Activity) {
                if (!activity.isChangingConfigurations) {
                    activityReferences--
                    Log.d("Check MyApp", "Activity destroyed. activityReferences: $activityReferences")
                    if (activityReferences <= 0) {
                        // 앱이 종료됨
                        onAppTerminated()
                    }
                }
            }

            private fun onAppBackgrounded() {
                // 앱이 백그라운드로 전환될 때 수행할 작업

                // 예: 데이터 저장, 리소스 해제, 로그 기록 등
            }

            private fun onAppTerminated(){
                Log.d("Check MyApp", "onAppTerminated : Location Service 종료")
                val serviceIntent = Intent(this@MyApp, LocationService::class.java)
                stopService(serviceIntent)
            }
        })
    }

    private fun setUpApp() {
        TTSUtil.initialize(this)
    }
}
