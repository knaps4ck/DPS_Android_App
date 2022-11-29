package com.example.dpsv2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dpsv2.fragments.DriverDropsFragment
import com.example.dpsv2.fragments.DriverPickupFragment
import com.example.dpsv2.fragments.DriverPreviousRidesFragment

private const val NUM_TABS = 3

public class DriverViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return DriverPickupFragment()
            1 -> return DriverDropsFragment()
        }
        return DriverPreviousRidesFragment()
    }
}