package com.example.sibal.activity.iactivity

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sibal.R

class IActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iactivity)

        // ViewCompat로 인셋 설정
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Intent에서 데이터 추출
        val programTitle = intent.getStringExtra("programTitle") // Intent에서 programTitle 추출

        // TextView에 데이터 설정
        val titleTextView = findViewById<TextView>(R.id.title) // title TextView 참조
        if (programTitle != null) {
            titleTextView.text = programTitle // title을 programTitle로 설정
        }
    }
}
