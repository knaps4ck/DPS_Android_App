package com.example.dpsv2.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.dpsv2.R
import com.example.dpsv2.adapters.DriverViewPagerAdapter
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
        val adapter = DriverViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = tablayoutArray[position]
        }.attach()

    }

    override fun onStart() {
        super.onStart()
        try {
            if (ContextCompat.checkSelfPermission(
                    applicationContext,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    101
                )

            }else{
                Intent(applicationContext, LocationService::class.java).apply {
                    action = LocationService.ACTION_START
                    startService(this)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}