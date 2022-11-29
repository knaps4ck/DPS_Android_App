package com.example.dpsv2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dpsv2.fragments.*

private const val NUM_TABS = 3

public class AdminViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return AdminDriversFragment()
            1 -> return AdminRequestsFragment()
        }
        return AdminPreviousRidesFragment()
    }
}