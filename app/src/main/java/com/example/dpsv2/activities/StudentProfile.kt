package com.example.dpsv2.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.dpsv2.R
import com.example.dpsv2.models.ApiResponse
import com.example.dpsv2.models.Student
import com.example.dpsv2.network.ApiInterface
import com.example.dpsv2.network.RetrofitClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StudentProfile : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAddress:EditText
    private lateinit var etEmail: EditText
    private lateinit var etContactNo:EditText
    private lateinit var etEmergency: EditText
    private lateinit var etSUID: EditText
    private var student: Student? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_profile)
        viewInitializations()

    }
    private fun viewInitializations() {
        val acct = GoogleSignIn.getLastSignedInAccount(this)

        etSUID = findViewById(R.id.et_suid)
        etName = findViewById(R.id.et_first_name)
        etAddress = findViewById(R.id.et_last_name)
        etEmail  = findViewById(R.id.et_email)
        etContactNo = findViewById(R.id.et_contact_no)
        etEmergency = findViewById(R.id.et_des)

        val sharedpref = this?.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
        etSUID.setText(sharedpref.getString("STUDENT_SUID",""))
        if (sharedpref.getString("STUDENT_SUID","") != "" && acct != null) {
            var retrofit = RetrofitClient.getInstance().create(ApiInterface::class.java)
            GlobalScope.launch {
                try {
                    val response = acct.email?.let { retrofit.getStudentByEmail(it) }
                    if (response != null) {
                        if (response.isSuccess) {

                            Log.d("STUDENTTT", response.isSuccess.toString())
                            student= response.result?.getStudent()
                            Log.e("STUDENT",student.toString())
                            etName.setText(student!!.name)
                            etAddress.setText(student!!.address)
                            etSUID.setText(student!!.suid.toString())
                            etContactNo.setText(student!!.contact_number)
                            etEmergency.setText(student!!.emergency_number)
                            etEmail.setText(student!!.email)

                        } else {
                            Toast.makeText(
                                this@StudentProfile,
                                response.error.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }catch (Ex:Exception){
                    Log.e("Error",Ex.toString())

                }
            }
        }else{
            etName.setText(sharedpref.getString("STUDENT_NAME",""))
            etAddress.setText(sharedpref.getString("STUDENT_ADDRESS",""))


            if (acct != null) {
                etEmail.setText(acct.email)
            }else{
                etEmail.setText(sharedpref.getString("STUDENT_EMAIL",""))
            }
            etContactNo.setText(sharedpref.getString("STUDENT_NUMBER",""))
            etEmergency.setText(sharedpref.getString("STUDENT_EMERGENCY",""))
        }
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