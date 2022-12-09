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
import com.example.dpsv2.adapters.AdminPrevRidesAdapter
import com.example.dpsv2.adapters.AdminViewPagerAdapter
import com.example.dpsv2.adapters.DriverDropAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminPreviousRidesFragment : Fragment() {

    private lateinit var prevlist : ArrayList<Rides>
    private lateinit var classConstants: Constants
    private lateinit var recyclerView: RecyclerView
    private lateinit var adminadapter: AdminPrevRidesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        classConstants = Constants()
        val view : View = inflater.inflate(R.layout.fragment_admin_previous_rides, container, false)
        val activity = activity as Context
        recyclerView = view.findViewById<RecyclerView>(R.id.admin_prev_recycler)
        prevlist = arrayListOf<Rides>()
        getRidesData()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }
    private fun getRidesData(){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("rides")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                prevlist.clear()
                if (snapshot.exists()){
                    for (ridesnapshot in snapshot.children){
                        val ride = ridesnapshot.getValue(Rides::class.java)
                        if (ride!!.ridestatus.equals(classConstants.PREVIEW_RIDE_STATUS)){
                            prevlist.add(ride)
                        }
                    }
                    adminadapter = AdminPrevRidesAdapter(
                        prevlist)
                    recyclerView.adapter = adminadapter
                    adminadapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }
}