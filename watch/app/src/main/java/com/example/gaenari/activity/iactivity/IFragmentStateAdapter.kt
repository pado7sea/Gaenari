package com.example.gaenari.activity.iactivity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gaenari.dto.response.FavoriteResponseDto

class IFragmentStateAdapter(fragmentActivity: FragmentActivity, private val  program: FavoriteResponseDto) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> IsecondFragment()
            1 -> IFirstFragment.newInstance(program) // 수정된 부분
            2 -> IsecondFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}