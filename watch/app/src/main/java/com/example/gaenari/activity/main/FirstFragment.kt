package com.example.gaenari.activity.main

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gaenari.R

class FirstFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle? // 'Bundle'을 'savedInstanceState'로 변경
    ): View? {
        val view = inflater.inflate(R.layout.fragment_first, container, false)

        val authBtn = view.findViewById<Button>(R.id.auth_btn)

        authBtn.setOnClickListener {
            val intent = Intent(activity, AuthActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}
