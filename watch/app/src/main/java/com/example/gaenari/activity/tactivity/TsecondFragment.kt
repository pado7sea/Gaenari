package com.example.gaenari.activity.tactivity
import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gaenari.R

class TSecondFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.fragment_tsecond, container, false)


        val pauseButton = view.findViewById<Button>(R.id.pauseButton)
        val stopButton = view.findViewById<Button>(R.id.stopButton)

        pauseButton.setOnClickListener {
            (activity as TActivity).pauseRunning() // 달리기 일시정지
        }

        stopButton.setOnClickListener {
            (activity as TActivity).stopRunning() // 달리기 종료
        }

        return view
    }
}