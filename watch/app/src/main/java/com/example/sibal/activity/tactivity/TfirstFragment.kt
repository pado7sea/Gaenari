package com.example.sibal.activity.tactivity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sibal.R
import com.example.sibal.viewmodel.RunViewModel

class TFirstFragment : Fragment() {
    private lateinit var timeTextView: TextView
    private lateinit var distanceTextView: TextView
    private lateinit var heartRateTextView: TextView
    private lateinit var 남은거리: TextView
    private lateinit var viewModel: RunViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_tfirst, container, false)

        남은거리 = view.findViewById(R.id.lesdis)
        timeTextView = view.findViewById(R.id.timeTextView)
        distanceTextView = view.findViewById(R.id.distanceTextView)
        heartRateTextView = view.findViewById(R.id.heartRateTextView)

        viewModel = ViewModelProvider(requireActivity()).get(RunViewModel::class.java)

        // ViewModel 데이터 관찰
        viewModel.runningTime.observe(viewLifecycleOwner) { time ->
            timeTextView.text = "$time"
        }
//
//        viewModel.targetDistance.observe(viewLifecycleOwner) { targetDistance ->
//            viewModel.distance.observe(viewLifecycleOwner) { distance ->
//                val remainingDistance = targetDistance - distance
//                남은거리.text = "%.2f".format(remainingDistance)
//            }
//        }
//        viewModel.distance.observe(viewLifecycleOwner) { distance ->
//            distanceTextView.text = "%.2f km".format(distance)
//        }

        viewModel.heartRate.observe(viewLifecycleOwner) { heartRate ->
            heartRateTextView.text = "$heartRate"
        }

        return view
    }
}