package com.example.dpsv2.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dpsv2.R
import com.example.dpsv2.models.Rides
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.math.floor

class StudentRideReview : AppCompatActivity() {
    private lateinit var startlocation: EditText
    private lateinit var destinationlocation: EditText
    private lateinit var confirmbutton: Button
    private lateinit var rides_counter: TextView
    private lateinit var wait_time_counter: TextView

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_ride_review)
        val source = intent.extras!!.getString("DESTINATION")
        val sharedpref = this.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val destination = sharedpref.getString("STUDENT_ADDRESS","1")

        startlocation = findViewById(R.id.start_location_edittext)
        destinationlocation = findViewById(R.id.destination_location_edittext)
        rides_counter = findViewById(R.id.rides_counter)
        wait_time_counter = findViewById(R.id.wait_time_counter)

        startlocation.setText(source)
        destinationlocation.setText(destination)

        confirmbutton = findViewById(R.id.confirm_button)
        confirmbutton.setOnClickListener {
            val suid = sharedpref.getString("STUDENT_SUID","1")
            if (findViewById<EditText>(R.id.suid_confirmation_edittext).text.toString() == suid) {
                val database = Firebase.database.reference
                val uuid = (floor(Math.random() * 9000000000L).toLong() + 1000000000L).toString()
                val ride = Rides(
                    rideID= uuid,
                    suid = suid,
                    source = source,
                    destination = destination
                )
                database.child("rides").child(uuid).setValue(ride)
                Toast.makeText(this, "DAta sent",Toast.LENGTH_SHORT).show()
                sharedpref.edit().putString("RECENT_RIDE_ID",uuid)
                sharedpref.edit().putString("RECENT_SOURCE",source)
                sharedpref.edit().putString("RECENT_DESTINATION",destination)
                sharedpref.edit().apply()

                val intent = Intent(this@StudentRideReview, StudentRideStatus::class.java)
                startActivity(intent)
            }else{
                findViewById<EditText>(R.id.suid_confirmation_edittext).error = "SUID Doesn't Match Your Account"
            }
        }

    }
}