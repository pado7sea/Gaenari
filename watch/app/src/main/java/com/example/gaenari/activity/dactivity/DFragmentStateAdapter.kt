package com.example.gaenari.activity.dactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DFragmentStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // 프래그먼트 수
    override fun getItemCount(): Int {
        return 4 // 네 개의 프래그먼트
    }

    // 인덱스에 따라 프래그먼트를 반환
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DSecondFragment() // 두 번째 프래그먼트 (제어)
            1 -> DFirstFragment() // 첫 번째 프래그먼트 (달린 정보)
            2 -> DSecondFragment() // 두 번째 프래그먼트 (제어)
            3 -> DFirstFragment() // 첫 번째 프래그먼트 (달린 정보)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}