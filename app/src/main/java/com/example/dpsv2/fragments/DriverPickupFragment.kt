package com.example.dpsv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.DriverPickupAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DriverPickupFragment : Fragment()  {
    private lateinit var pickuplist : ArrayList<Rides>
    private lateinit var classConstants: Constants
    private lateinit var recyclerView: RecyclerView
    private lateinit var pickupadapter: DriverPickupAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classConstants = Constants()
        val view: View = inflater.inflate(R.layout.fragment_driver_pickup, container, false)
        val activity = activity as Context
        recyclerView = view.findViewById<RecyclerView>(R.id.driver_pickup_recucler)
        pickuplist = arrayListOf<Rides>()
        getRidesData()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    private fun getRidesData(){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("rides")
        database.addValueEventListener(object : ValueEventListener{
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                pickuplist.clear()
                if (snapshot.exists()){
                    for (ridesnapshot in snapshot.children){
                        val ride = ridesnapshot.getValue(Rides::class.java)
                        if (ride!!.ridestatus.equals(classConstants.PICKUP_RIDE_STATUS)){
                            Log.d("Fortestring", ride.toString())
                            pickuplist.add(ride)
                        }
                    }

                    pickupadapter = DriverPickupAdapter(
                        pickuplist,
                        ::onNavigateClickCallback,
                        ::onPickupClickCallback)
                    recyclerView.adapter = pickupadapter
                    pickupadapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }



    fun onNavigateClickCallback(ride: Rides) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${ride.source}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onPickupClickCallback(ride: Rides) {
        Toast.makeText(context, ride.rideID, Toast.LENGTH_SHORT).show()
        val database = Firebase.database.reference
        database.child("rides")
            .child(ride.rideID!!)
            .child("ridestatus")
            .setValue(classConstants.DROP_RIDE_STATUS)

        pickupadapter.notifyDataSetChanged()

    }
}