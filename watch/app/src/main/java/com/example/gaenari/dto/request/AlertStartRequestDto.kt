package com.example.gaenari.dto.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class AlertStartRequestDto(
    val exerciseDateTime: LocalDateTime,
    val programTitle: String,
    val programType: String?
): Parcelable
