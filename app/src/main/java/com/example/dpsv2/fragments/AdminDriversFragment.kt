package com.example.dpsv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.AdminDriverAdapter
import com.example.dpsv2.adapters.AdminPrevRidesAdapter
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList


class AdminDriversFragment : Fragment() {
    private lateinit var classConstants: Constants
    private lateinit var driverAddList:  ArrayList<String>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adminadapter: AdminDriverAdapter
    val database = FirebaseDatabase.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        classConstants = Constants()
        driverAddList = ArrayList<String>()
        val view: View = inflater.inflate(R.layout.fragment_admin_drivers, container, false)
        val geocoder = Geocoder(context, Locale.getDefault())
        recyclerView = view.findViewById<RecyclerView>(R.id.admin_driverAdd_recycler)

        val driverList = arrayOf("driver", "driverA", "driverB")
        for (driver in driverList) {
            val connection = database.getReference(driver)

            connection.addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val lat = snapshot.child("latitude").value
                        val long = snapshot.child("longitude").value
                        val address = geocoder.getFromLocation(lat as Double, long as Double, 1)
                        driverAddList.add(address[0].getAddressLine(0))
                    }
                    adminadapter = AdminDriverAdapter(driverAddList, driverList)
                    recyclerView.adapter = adminadapter
                    adminadapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {
                    throw error.toException()
                }
            })
        }
        val activity = activity as Context
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }



}