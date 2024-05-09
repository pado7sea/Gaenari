//package com.example.gaenari.database
//
//import androidx.lifecycle.MutableLiveData
//
//class RunningRepository(private val runningSessionDao: RunningSessionDao) {
//    val runningData = MutableLiveData<RunningData>()
//
//    fun updateRunningData(distance: Double, heartRate: Float, speed: Float, elapsedTime: Long) {
//        val runningData = RunningData(distance, heartRate, speed, elapsedTime)
//        this.runningData.postValue(runningData)
//    }
//
//    suspend fun saveSession(distance: Double, averageHeartRate: Float, averageSpeed: Float, timestamp: Long) {
//        val session = RunningSession(0, distance, averageHeartRate, averageSpeed, timestamp)
//        runningSessionDao.insertSession(session)
//    }
//}
