package com.example.gaenari.data

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DRunningViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DRunningViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DRunningViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}