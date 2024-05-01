package com.example.gaenari.activity.tactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TFragmentStateAdapter(fragmentActivity: FragmentActivity, private val programTarget: Int,private val programType: String,private val programTitle: String,private val programId: Long ) : FragmentStateAdapter(fragmentActivity) {
    // 프래그먼트 수
    override fun getItemCount(): Int = 3

    // 인덱스에 따라 프래그먼트를 반환
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TSecondFragment() // 두 번째 프래그먼트 (제어)
            1 -> TFirstFragment.newInstance(programTarget,programType,programTitle,programId) // 첫 번째 프래그먼트 (달린 정보)
            2 -> TSecondFragment() // 두 번째 프래그먼트 (제어)
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}