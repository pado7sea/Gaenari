package com.example.gaenari.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gaenari.activity.dactivity.DsendData

class DRunningViewModel(application: Application) : AndroidViewModel(application){

    // RunningData를 위한 LiveData 선언
    private val _runningData = MutableLiveData<DsendData>()
    val runningData: LiveData<DsendData> get() = _runningData

    // 1초마다 수집된 속력과 심박수 임시 저장소
    private val secondSpeedList = mutableListOf<Double>()
    private val secondHeartRateList = mutableListOf<Int>()


    // LiveData 선언 (현재 속도, 현재 심박수)
    private val _currentSpeed = MutableLiveData<Double>()
    val currentSpeed: LiveData<Double> get() = _currentSpeed

    private val _currentHeartRate = MutableLiveData<Int>()
    val currentHeartRate: LiveData<Int> get() = _currentHeartRate

    private val _programTitle = MutableLiveData<String>()
    val programTitle: LiveData<String> get() = _programTitle

    private val _programTarget = MutableLiveData<Int>()
    val programTarget: LiveData<Int> get() = _programTarget

    //일시정지
    private val _isPaused = MutableLiveData<Boolean>(false)
    val isPaused: LiveData<Boolean> get() = _isPaused

    fun pauseAllServices() {
        _isPaused.value = true
    }

    fun resumeAllServices() {
        _isPaused.value = false
    }

    // 데이터 초기값 설정
    init {
        val initialRecord = DsendData.Record(0.0, 0)
        val initialSpeeds = DsendData.Speeds(0.0, mutableListOf())
        val initialHeartRates = DsendData.HeartRates(0, mutableListOf())
        val initialProgram = DsendData.Program(
            programId = 0
        )
        _runningData.value = DsendData(
            date = "1970-01-01",
            exerciseType = "P",
            programType = "D",
            program = initialProgram,
            record = initialRecord,
            speeds = initialSpeeds,
            heartrates = initialHeartRates
        )
    }

    // 속도 수집 메서드
    fun addSecondSpeed(speed: Double) {
        // 1초 단위 속도 데이터를 리스트에 추가
        secondSpeedList.add(speed)
        _currentSpeed.value = speed

        // 60개 (1분)가 쌓이면 평균 계산
        if (secondSpeedList.size >= 60) {
            val averageSpeed = secondSpeedList.average()

            // 평균을 Speeds 배열에 추가
            val updatedSpeeds = _runningData.value?.speeds ?: DsendData.Speeds(0.0, mutableListOf())
            updatedSpeeds.arr.add(averageSpeed.toInt())

            // RunningData 업데이트
            _runningData.value = _runningData.value?.copy(speeds = updatedSpeeds)

            // 1초 리스트 초기화
            secondSpeedList.clear()
        }
    }

    // 심박수 수집 메서드
    fun addSecondHeartRate(heartRate: Int) {
        // 1초 단위 심박수 데이터를 리스트에 추가
        secondHeartRateList.add(heartRate)
        _currentHeartRate.value = heartRate

        // 60개 (1분)가 쌓이면 평균 계산
        if (secondHeartRateList.size >= 60) {
            val averageHeartRate = secondHeartRateList.average().toInt()

            // 평균을 HeartRates 배열에 추가
            val updatedHeartRates = _runningData.value?.heartrates ?: DsendData.HeartRates(0, mutableListOf())
            updatedHeartRates.arr.add(averageHeartRate)

            // RunningData 업데이트
            _runningData.value = _runningData.value?.copy(heartrates = updatedHeartRates)

            // 1초 리스트 초기화
            secondHeartRateList.clear()
        }
    }

    // 데이터 업데이트 메서드
    fun updateRunningData(
        date: String,
        exerciseType: String,
        programType: String,
        program: DsendData.Program,
        record: DsendData.Record,
        speeds: DsendData.Speeds,
        heartrates: DsendData.HeartRates
    ) {
        _runningData.value = DsendData(
            date = date,
            exerciseType = exerciseType,
            programType = programType,
            program = program,
            record = record,
            speeds = speeds,
            heartrates = heartrates
        )
    }

    // 개별 필드 업데이트 메서드
    fun setRecord(record: DsendData.Record) {
        _runningData.value = _runningData.value?.copy(record = record)
    }

    fun setSpeeds(speeds: DsendData.Speeds) {
        _runningData.value = _runningData.value?.copy(speeds = speeds)
    }

    fun setHeartRates(heartrates: DsendData.HeartRates) {
        _runningData.value = _runningData.value?.copy(heartrates = heartrates)
    }
    fun setProgramTitle(newProgramTitle: String) {
        _programTitle.value = newProgramTitle
    }
    fun setProgramTarget(newProgramTarget: Int) {
        _programTarget.value = newProgramTarget
    }

    fun setProgramId(newProgramId: Long) {
        // 현재 `DsendData` 가져오기
        val currentRunningData = _runningData.value

        // 현재 `program`이 없다면 아무 작업도 하지 않고 종료
        val currentProgram = currentRunningData?.program ?: return

        // `programId`만 업데이트된 새로운 Program 객체 생성
        val updatedProgram = currentProgram.copy(programId = newProgramId)

        // 전체 `DsendData`에서 Program만 변경하여 업데이트
        _runningData.value = currentRunningData.copy(program = updatedProgram)
    }

    fun setDate(newDate: String) {
        // 현재 `RunningData` 가져오기
        val currentRunningData = _runningData.value

        // `RunningData`의 기존 값을 유지하며 `date` 필드만 업데이트
        _runningData.value = currentRunningData?.copy(date = newDate)
    }

    // 데이터 초기화 메서드
    fun clearData() {
        val initialRecord = DsendData.Record(0.0, 0)
        val initialSpeeds = DsendData.Speeds(0.0, mutableListOf())
        val initialHeartRates = DsendData.HeartRates(0, mutableListOf())
        val initialProgram = DsendData.Program(
            programId = 0,
        )
        _runningData.value = DsendData(
            date = "1970-01-01",
            exerciseType = "P",
            programType = "D",
            program = initialProgram,
            record = initialRecord,
            speeds = initialSpeeds,
            heartrates = initialHeartRates
        )
    }
}


