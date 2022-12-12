package com.example.dpsv2.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.cuneytayyildiz.onboarder.OnboarderActivity
import com.cuneytayyildiz.onboarder.model.OnboarderImage
import com.cuneytayyildiz.onboarder.model.OnboarderPage
import com.cuneytayyildiz.onboarder.model.OnboarderText
import com.cuneytayyildiz.onboarder.model.onboarderPage
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener
import com.cuneytayyildiz.onboarder.utils.color
import com.example.dpsv2.MainActivity
import com.example.dpsv2.R


class IntroSliderActivity : OnboarderActivity(), OnboarderPageChangeListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnboarderPageChangeListener(this)

        val pages: MutableList<OnboarderPage> = createOnboarderPages()
        initOnboardingPages(pages)

    }

    override fun onResume() {
        super.onResume()
        val isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
            .getBoolean("isFirstRun", true)
        if (!isFirstRun) {
            startActivity(Intent(this@IntroSliderActivity, MainActivity::class.java))
        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
            .putBoolean("isFirstRun", false).commit()
    }

    private fun createOnboarderPages(): MutableList<OnboarderPage> {
        return mutableListOf(
            onboarderPage {
                backgroundColor = color(R.color.teal_200)

                image = OnboarderImage(
                    imageResId = R.drawable.orangesafe
                )

                title = OnboarderText(
                    text = "Orange Safe App \n" +
                            "Escort Shuttle",
                    textColor  = color(R.color.black)
                )

                description  = OnboarderText(
                    text = "Book DPS shuttle for students \n" +
                            "from 8.00 pm to 6.00 am",
                    textColor = color(R.color.black),
                    multilineCentered = true
                )
            },onboarderPage {
                backgroundColor = color(android.R.color.holo_orange_dark)

                image = OnboarderImage(
                    imageResId = R.drawable.delivery
                )

                title = OnboarderText(
                    text = "Get Your Route\n" +
                            "Optimized",
                    textColor  = color(R.color.black)
                )

                description  = OnboarderText(
                    text = "The App will help DPS admins\n" +
                            "to cluster ride requests",
                    textColor = color(R.color.black),
                    multilineCentered = true
                )
            },onboarderPage {
                backgroundColor = color(android.R.color.holo_purple)

                image = OnboarderImage(
                    imageResId = R.drawable.driver
                )

                title = OnboarderText(
                    text = "Drivers get \n" +
                            "GMaps Integration",
                    textColor  = color(R.color.black)
                )

                description  = OnboarderText(
                    text = "Drivers Get Best Route to drop \n" +
                            "each students in lowest time",
                    textColor = color(R.color.black),
                    multilineCentered = true
                )
            }
        )
    }


    override fun onFinishButtonPressed() {
        finish()
        val intent = Intent(this@IntroSliderActivity, MainActivity::class.java)
        startActivity(intent)

    }

    override fun onPageChanged(position: Int) {
        Log.d(javaClass.simpleName, "onPageChanged: $position")
    }
}