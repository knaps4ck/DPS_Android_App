package com.example.dpsv2.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.dpsv2.R

class StudentProfile : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAddress:EditText
    private lateinit var etEmail: EditText
    private lateinit var etContactNo:EditText
    private lateinit var etEmergency: EditText
    private lateinit var etSUID: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        viewInitializations()

    }
    private fun viewInitializations() {

        etSUID = findViewById(R.id.et_suid)
        etName = findViewById(R.id.et_first_name)
        etAddress = findViewById(R.id.et_last_name)
        etEmail  = findViewById(R.id.et_email)
        etContactNo = findViewById(R.id.et_contact_no)
        etEmergency = findViewById(R.id.et_des)

        val sharedpref = this?.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        etSUID.setText(sharedpref.getString("STUDENT_SUID","1"))
        etName.setText(sharedpref.getString("STUDENT_NAME","2"))
        etAddress.setText(sharedpref.getString("STUDENT_ADDRESS","3"))
        etEmail.setText(sharedpref.getString("STUDENT_EMAIL","4"))
        etContactNo.setText(sharedpref.getString("STUDENT_NUMBER","5"))
        etEmergency.setText(sharedpref.getString("STUDENT_EMERGENCY","6"))


        // To show back button in actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    // Checking if the input in form is valid
    private fun validateInput(): Boolean {
        if (etName.text.toString().equals("")) {
            etName.setError("Please Enter Name")
            return false
        }
        if (etAddress.text.toString().equals("")) {
            etAddress.setError("Please Enter Address")
            return false
        }
        if (etEmail.text.toString().equals("")) {
            etEmail.setError("Please Enter Email")
            return false
        }

        if (etContactNo.text.toString().equals("")) {
            etContactNo.setError("Please Enter Contact No")
            return false
        }
        if (etSUID.text.toString().equals("")) {
            etSUID.setError("Please Enter SUID No")
            return false
        }
        if (etEmergency.text.toString().equals("")) {
            etEmergency.setError("Please Enter Emergency Contact No")
            return false
        }
        // checking the proper email format
        if (!isEmailValid(etEmail.text.toString())) {
            etEmail.setError("Please Enter Valid Email")
            return false
        }

        return true
    }

    private fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Hook Click Event

    fun performEditProfile (view: View) {
        if (validateInput()) {

            // Input is valid, here send data to your server

            val name = etName.text.toString()
            val address = etAddress.text.toString()
            val email = etEmail.text.toString()
            val contactNo = etContactNo.text.toString()
            val emergencyNumber = etEmergency.text.toString()
            val suid = etSUID.text.toString()

            val sharedpref = this.getSharedPreferences("dpsv2", Context.MODE_PRIVATE)
            val prefeditor = sharedpref.edit()

            prefeditor.putString("STUDENT_NAME",name)
            prefeditor.putString("STUDENT_ADDRESS",address)
            prefeditor.putString("STUDENT_EMAIL",email)
            prefeditor.putString("STUDENT_NUMBER",contactNo)
            prefeditor.putString("STUDENT_EMERGENCY",emergencyNumber)
            prefeditor.putString("STUDENT_SUID",suid)

            prefeditor.apply()

            Toast.makeText(this,"Profile Update Successfully", Toast.LENGTH_SHORT).show()
            // Here you can call you API

            finish()
            val intent = Intent(this@StudentProfile, StudentHome::class.java)
            startActivity(intent)

        }
    }
}