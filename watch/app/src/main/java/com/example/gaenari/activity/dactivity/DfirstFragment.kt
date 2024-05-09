package com.example.gaenari.activity.dactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gaenari.R
import com.example.gaenari.data.DRunningViewModel

class DFirstFragment : Fragment() {

    private lateinit var drunningViewModel: DRunningViewModel
    private lateinit var distanceView: TextView
    private lateinit var timeView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var speedView: TextView
    private lateinit var circleProgress: DCircleProgress

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dfirst, container, false)
        distanceView = view.findViewById(R.id.달린거리)
        timeView = view.findViewById(R.id.달린시간)
        heartRateView = view.findViewById(R.id.심박수)
        speedView = view.findViewById(R.id.속력)
        circleProgress = view.findViewById(R.id.circleProgress)

        // 뷰모델 인스턴스 생성
        drunningViewModel = ViewModelProvider(requireActivity()).get(DRunningViewModel::class.java)

        // 데이터 관찰 및 UI 업데이트
        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        // 거리 업데이트
        drunningViewModel.runningData.observe(viewLifecycleOwner, Observer { runningData ->
            distanceView.text = String.format("%.2f km", runningData.record.distance)
            // 여기서 필요한 데이터에 따라 다른 View들을 업데이트하세요
        })

        // 속도 업데이트
        drunningViewModel.currentSpeed.observe(viewLifecycleOwner, Observer { speed ->
            speedView.text = String.format("%.2f km/h", speed * 3.6)
        })

        // 심박수 업데이트
        drunningViewModel.currentHeartRate.observe(viewLifecycleOwner, Observer { heartRate ->
            heartRateView.text = heartRate.toString()
        })

        // 필요에 따라 타이머 및 기타 데이터도 추가로 관찰하고 업데이트
    }
}
