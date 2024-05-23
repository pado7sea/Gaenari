package com.example.gaenari

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StepCounterViewModel : ViewModel() {
    private val _stepCount = MutableLiveData(0) // 초기 걸음 수
    val stepCount: LiveData<Int> = _stepCount

    fun updateStepCount(newStepCount: Int) {
        _stepCount.value = newStepCount // 데이터 업데이트
    }
}
