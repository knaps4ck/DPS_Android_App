package com.example.dpsv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.DriverPickupAdapter
import com.example.dpsv2.adapters.DriverPreviousRidesAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.*


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
        val view : View =  inflater.inflate(R.layout.fragment_driver_previous_rides, container, false)
        val activity = activity as Context
        recyclerView = view.findViewById(R.id.driver_prev_recycler)
        rideslist = arrayListOf()
        getPrevRidesData()
        recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    private  fun getPrevRidesData(){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("rides")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                rideslist.clear()
                if (snapshot.exists()){
                    for (ridesnapshot in snapshot.children){
                        val ride = ridesnapshot.getValue(Rides::class.java)
                        if (ride!!.ridestatus.equals(classConstants.PREVIEW_RIDE_STATUS)){
                            rideslist.add(ride)
                        }
                    }

                    previousridesAdapter = DriverPreviousRidesAdapter(
                        rideslist)
                    recyclerView.adapter = previousridesAdapter
                    previousridesAdapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }

}