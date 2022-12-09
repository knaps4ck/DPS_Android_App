package com.example.dpsv2

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.dpsv2.activities.DriverHome
import com.example.dpsv2.activities.StudentHome
import com.example.dpsv2.activities.StudentProfile
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var  gso : GoogleSignInOptions;
    private lateinit var  gsc : GoogleSignInClient;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                dialog()
            }
        }
        this.onBackPressedDispatcher.addCallback(this, callback)



        var googleBtn = findViewById<TextView>(R.id.student_login);

        gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(this, gso)

        val acct = GoogleSignIn.getLastSignedInAccount(this)

        if (acct != null) {
            navigateToSecondActivity()
        }else{
            val sharedpref = this.getSharedPreferences("dpsv2",MODE_PRIVATE) ?: return
            sharedpref.edit().clear().commit()
        }
        googleBtn.setOnClickListener {
            signIn()
        }

        findViewById<Button>(R.id.driver_login).setOnClickListener {
            val intent = Intent(this@MainActivity, DriverHome::class.java)
            startActivity(intent)
        }

    }
    private fun signIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, 1000)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1000) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)
                navigateToSecondActivity()
            } catch (e: ApiException) {
                Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }
    private fun navigateToSecondActivity() {
        finish()
        val intent = Intent(this@MainActivity, StudentProfile::class.java)
        startActivity(intent)
    }


     fun dialog() {
        val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)

        builder.setTitle("Exit ?")
        builder.setMessage("Are you sure you want to exit?")

        builder.setPositiveButton(
            "YES",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                dialog.dismiss()
                moveTaskToBack(true);
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
            })

        builder.setNegativeButton(
            "NO",
            DialogInterface.OnClickListener { dialog, _ -> // Do nothing
                dialog.dismiss()
            })

        builder.create()?.show()
    }
}