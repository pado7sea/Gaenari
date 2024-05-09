package com.example.gaenari.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.gaenari.dto.response.FavoriteResponseDto

class SharedViewModel : ViewModel() {
    private val _favoritePrograms = MutableLiveData<List<FavoriteResponseDto>>()
    val favoritePrograms: LiveData<List<FavoriteResponseDto>> = _favoritePrograms

    // 프로그램 리스트를 전달받아 null 값이 아닌 데이터만 저장
    fun setFavoritePrograms(programs: List<FavoriteResponseDto?>) {
        // null 값이 아닌 프로그램만 걸러냅니다.
        val nonNullPrograms = programs.filterNotNull()
        _favoritePrograms.value = nonNullPrograms
    }
}
