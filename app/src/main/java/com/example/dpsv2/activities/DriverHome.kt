package com.example.dpsv2.activities

import android.content.Intent
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.dpsv2.R
import com.example.dpsv2.adapters.ViewPagerAdapter
import com.example.dpsv2.utils.LocationService
import com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.location.LocationRequest.create
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DriverHome : AppCompatActivity() {
    val tablayoutArray = arrayOf(
        "Pickup",
        "Drop",
        "Previous Rides"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_home)
        supportActionBar?.hide()

        val viewPager = findViewById<ViewPager2>(R.id.driverhome_viewpager)
        val tabLayout = findViewById<TabLayout>(R.id.driverhome_tablayout)
        val adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tablayoutArray[position]
        }.attach()

    }

    override fun onStart() {
        super.onStart()
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }
}