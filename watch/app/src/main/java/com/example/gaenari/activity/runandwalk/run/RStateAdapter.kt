package com.example.gaenari.activity.runandwalk.run

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class RStateAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    // 프래그먼트 수
    override fun getItemCount(): Int = 3

    // 인덱스에 따라 프래그먼트를 반환
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> RSecondFragment() // 두 번째 프래그먼트 (제어)
            1 -> RFirstFragment() // 첫 번째 프래그먼트 (달린 정보)
            2 -> RSecondFragment() // 두 번째 프래그먼트 (제어)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}