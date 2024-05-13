package com.example.gaenari.dto.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class SaveDataRequestDto(
    val date: LocalDateTime = LocalDateTime.now(),
    val exerciseType: String,
    val programType: String?,
    val program: Program?,
    val record: Record,
    val speeds: Speeds,
    val heartrates: HeartRates
) : Parcelable

@Parcelize
data class Program(
    val programId: Long,
    val intervalInfo: IntervalInfo?
) : Parcelable

@Parcelize
data class IntervalInfo(
    val ranges: MutableList<Ranges>
) : Parcelable {
    fun addRange(range: Ranges) {
        ranges.add(range)
    }
}

@Parcelize
data class Ranges(
    val isRunning: Boolean,
    val time: Int,
    val speed: Double
) : Parcelable

@Parcelize
data class Record(
    var distance: Double,
    var time: Double
) : Parcelable

@Parcelize
data class Speeds(
    var average: Double,
    var arr: MutableList<Double>
) : Parcelable {
    fun addSpeed(double: Double){
        arr.add(double)
    }
}

@Parcelize
data class HeartRates(
    var average: Int,
    val arr: MutableList<Int>
) : Parcelable {
    fun addHeartRate(int: Int){
        arr.add(int)
    }
}

