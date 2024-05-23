package com.example.gaenari.dto.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MyPetResponseDto(
    val id: Long,
    val name: String,
    val affection: Int,
    val tier: String,
    val changeTime: String
) : Parcelable
