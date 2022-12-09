package com.example.dpsv2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.DriverPickupAdapter
import com.example.dpsv2.adapters.DriverPreviousRidesAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants


class DriverPreviousRidesFragment : Fragment() {
    private lateinit var rideslist : ArrayList<Rides>
    private lateinit var classConstants: Constants
    private lateinit var recyclerView: RecyclerView
    private lateinit var previousridesAdapter: DriverPreviousRidesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classConstants = Constants()
        return inflater.inflate(R.layout.fragment_driver_previous_rides, container, false)
    }


}