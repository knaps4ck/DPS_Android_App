package com.example.dpsv2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.dpsv2.R

class StudentRideStatus : AppCompatActivity() {
    private lateinit var rideIDtext: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_ride_status)
        val sharedpref = this?.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val rideID = sharedpref.getString("RECENT_RIDE","No Active Requests")

        rideIDtext = findViewById(R.id.rideid_text)
        rideIDtext.text = rideID
    }
}