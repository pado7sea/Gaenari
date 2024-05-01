package com.example.gaenari.activity.dactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class DFragmentStateAdapter(fragmentActivity: FragmentActivity, private val programTarget: Int) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DSecondFragment()
            1 -> DFirstFragment.newInstance(programTarget) // 수정된 부분
            2 -> DSecondFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}