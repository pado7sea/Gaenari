package com.example.sibal.activity.iactivity

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sibal.R

class IsecondFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? //
    ): View? {
        return inflater.inflate(R.layout.fragment_isecond, container, false)
    }
}
