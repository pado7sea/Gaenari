package com.example.gaenari.activity.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gaenari.SecondFragment

class MyFragmentStateAdapter(fragment: HomeActivity) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 5 // 3개의 프래그먼트
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ThirdFragment() // 세 번째 프래그먼트
            1 -> FirstFragment() // 첫 번째 프래그먼트
            2 -> SecondFragment() // 두 번째 프래그먼트
            3 -> ThirdFragment() // 세 번째 프래그먼트
            4 -> FirstFragment() // 첫 번째 프래그먼트
            else -> throw IndexOutOfBoundsException()
        }
    }
}