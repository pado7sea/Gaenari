package com.example.gaenari.activity.main

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gaenari.R

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? // 'Bundle'을 'savedInstanceState'로 변경
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }
}
