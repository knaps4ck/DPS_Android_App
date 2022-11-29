package com.example.dpsv2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.dpsv2.R
import com.example.dpsv2.adapters.AdminViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class AdminHome : AppCompatActivity() {
    val tablayoutArray = arrayOf(
        "Drivers",
        "Requests",
        "Previous Rides"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)
        supportActionBar?.hide()

        val viewPager = findViewById<ViewPager2>(R.id.adminhome_viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.adminhome_tablayout)
        val adapter = AdminViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tablayoutArray[position]
        }.attach()
    }
}