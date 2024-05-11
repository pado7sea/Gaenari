package com.example.gaenari.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class FavoriteResponseDto(
    val programId: Long,
    val programTitle: String,
    val usageCount: Int, // 운동 프로그램 총 사용횟수
    val finishedCount: Int, // 운동 프로그램 완주 횟수
    val type: String, // enum: D(거리목표), T(시간목표), I(인터벌)
    val program: ProgramTypeInfoDto
) : Parcelable

@Parcelize
data class ProgramTypeInfoDto(
    val targetValue: Double?,
    val intervalInfo: IntervalDto?,
) : Parcelable

@Parcelize
data class IntervalDto(

    val duration: Double?,  // 인터벌 총 소요 시간
    val setCount: Int?,  // 세트 수
    val rangeCount: Int?, // 세트 당 구간 수
    val ranges: List<RangeDto>? // 구간 리스트
) : Parcelable

@Parcelize
data class RangeDto(
    val id: Long?,
    val isRunning: Boolean?,  // true:뛰는 시간, false: 걷는 시간
    val time: Double?,  // 단위: sec
    val speed: Double? // 단위: km/h
) : Parcelable