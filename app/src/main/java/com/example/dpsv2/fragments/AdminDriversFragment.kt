package com.example.dpsv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dpsv2.R
import com.example.dpsv2.utils.Constants


class AdminDriversFragment : Fragment() {
    private lateinit var classConstants: Constants



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        classConstants = Constants()
        val view: View = inflater.inflate(R.layout.fragment_admin_drivers, container, false)



        return view
    }

}