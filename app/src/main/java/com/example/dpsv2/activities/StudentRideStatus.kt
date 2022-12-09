package com.example.dpsv2.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dpsv2.R
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class StudentRideStatus : AppCompatActivity() {
    private lateinit var rideIDtext: TextView
    private lateinit var startlocation: TextView
    private lateinit var endlocation: TextView
    private lateinit var cancel_button: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_ride_status)
        val sharedpref = this?.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        val prefeditor = sharedpref.edit()

        val rideID = sharedpref.getString("RECENT_RIDE_ID","No Active Requests")
        val source = sharedpref.getString("RECENT_SOURCE","No Active Requests")
        val end = sharedpref.getString("RECENT_DESTINATION","No Active Requests")

        rideIDtext = findViewById(R.id.rideid_text)
        startlocation = findViewById(R.id.start_location)
        endlocation = findViewById(R.id.end_location)
        cancel_button = findViewById(R.id.cancel_request_button)

        rideIDtext.text = "#$rideID"
        startlocation.text = source
        endlocation.text = end

        cancel_button.setOnClickListener {
            val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

            builder.setTitle("Confirm")
            builder.setMessage("Are you sure you want to cancel this ride?")

            builder.setPositiveButton(
                "YES",
                DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                    val database = Firebase.database.reference
                    if (rideID != null) {
                        database.child("rides").child(rideID)
                            .child("ridestatus").setValue("5")
                        prefeditor.remove("RECENT_RIDE_ID").apply()
                        prefeditor.remove("RECENT_SOURCE").apply()
                        prefeditor.remove("RECENT_DESTINATION").apply()
                    }
                    dialog.dismiss()
                    finish()
                    val intent = Intent(this@StudentRideStatus, StudentHome::class.java)
                    startActivity(intent)
                })

            builder.setNegativeButton(
                "NO",
                DialogInterface.OnClickListener { dialog, _ -> // Do nothing
                    dialog.dismiss()
                })

            builder.create()?.show()
        }
    }
}