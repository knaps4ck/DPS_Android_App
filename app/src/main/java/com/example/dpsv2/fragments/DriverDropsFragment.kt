package com.example.dpsv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.DriverDropAdapter
import com.example.dpsv2.adapters.DriverPickupAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class DriverDropsFragment : Fragment() {
    private lateinit var droplist : ArrayList<Rides>
    private lateinit var classConstants: Constants
    private lateinit var recyclerView: RecyclerView
    private lateinit var dropadapter: DriverDropAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classConstants = Constants()
        val view: View = inflater.inflate(R.layout.fragment_driver_drops, container, false)
        val activity = activity as Context
        recyclerView = view.findViewById<RecyclerView>(R.id.driver_drops_recycler)
        droplist = arrayListOf<Rides>()
        getRidesData()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    private fun getRidesData(){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("rides")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                droplist.clear()
                if (snapshot.exists()){
                    for (ridesnapshot in snapshot.children){
                        val ride = ridesnapshot.getValue(Rides::class.java)
                        if (ride!!.ridestatus.equals(classConstants.DROP_RIDE_STATUS)){
                            droplist.add(ride)
                        }
                    }
                    dropadapter = DriverDropAdapter(
                        droplist,
                        ::onNavigateClickCallback,
                        ::onDropClickCallback)
                    recyclerView.adapter = dropadapter
                    dropadapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }


    fun onNavigateClickCallback(ride: Rides) {
        val gmmIntentUri = Uri.parse("geo:0,0?q=${ride.destination}")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun onDropClickCallback(ride: Rides) {
        Toast.makeText(context, ride.rideID, Toast.LENGTH_SHORT).show()
        val database = Firebase.database.reference
        database.child("rides")
            .child(ride.rideID!!)
            .child("ridestatus")
            .setValue(classConstants.PREVIEW_RIDE_STATUS)

        dropadapter.notifyDataSetChanged()

    }
}