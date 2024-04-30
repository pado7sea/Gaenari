package com.example.sibal.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.concurrent.TimeUnit

class RunViewModel : ViewModel() {
    private val _runningTime = MutableLiveData<Long>()  // 경과 시간
    val runningTime: LiveData<Long> get() = _runningTime

    private val _totalDistance = MutableLiveData<Double>()  // 총 거리
    val totalDistance: LiveData<Double> get() = _totalDistance

    private val _heartRate = MutableLiveData<Double>()  // 심박수
    val heartRate: LiveData<Double> get() = _heartRate

    private val _averageHeartRate = MutableLiveData<Double>()  // 평균 심박수
    val averageHeartRate: LiveData<Double> get() = _averageHeartRate

    private val _averageSpeed = MutableLiveData<Double>()  // 평균 속도
    val averageSpeed: LiveData<Double> get() = _averageSpeed

    private val _targetDistance = MutableLiveData<Double>()  // 목표 거리
    val targetDistance: LiveData<Double> get() = _targetDistance



    fun updateTargetDistance(distance: Double) {
        _targetDistance.value = distance
    }

    fun incrementTime(time: Long, unit: TimeUnit) {
        val current = _runningTime.value ?: 0L
        _runningTime.value = current + unit.toSeconds(time)  // 시간 증가
    }

    fun updateDistance(distance: Double) {
        _totalDistance.value = distance  // 총 거리 업데이트
    }

    fun updateHeartRate(heartRate: Double) {
        _heartRate.value = heartRate  // 심박수 업데이트
    }

    fun updateAverageHeartRate(averageHeartRate: Double) {
        _averageHeartRate.value = averageHeartRate  // 평균 심박수 업데이트
    }

    fun updateAverageSpeed(averageSpeed: Double) {
        _averageSpeed.value = averageSpeed  // 평균 속도 업데이트
    }
}
