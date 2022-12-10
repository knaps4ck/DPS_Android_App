package com.example.dpsv2.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.adapters.AdminPrevRidesAdapter
import com.example.dpsv2.adapters.AdminRequestsAdapter
import com.example.dpsv2.models.Rides
import com.example.dpsv2.utils.Constants
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AdminRequestsFragment : Fragment() {
    private lateinit var reqlist : ArrayList<Rides>
    private lateinit var classConstants: Constants
    private lateinit var recyclerView: RecyclerView
    private lateinit var adminreqadapter: AdminRequestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        classConstants = Constants()

        val view : View =  inflater.inflate(R.layout.fragment_admin_requests, container, false)
        val activity = activity as Context
        recyclerView = view.findViewById<RecyclerView>(R.id.admin_req_recycler)
        reqlist = arrayListOf<Rides>()
        getRidesData(requireContext())
        recyclerView.layoutManager = LinearLayoutManager(activity)

        return view
    }
    private fun getRidesData(context: Context){
        val database : DatabaseReference = FirebaseDatabase.getInstance().getReference("rides")
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                reqlist.clear()
                if (snapshot.exists()){
                    for (ridesnapshot in snapshot.children){
                        val ride = ridesnapshot.getValue(Rides::class.java)
                        if (ride!!.ridestatus.equals(classConstants.NEW_RIDE_STATUS)){
                            reqlist.add(ride)
                        }
                    }
                    adminreqadapter = AdminRequestsAdapter(
                        reqlist,
                        ::onAcceptClickCallback,
                        ::onRejectClickCallback,
                        context!!)
                    recyclerView.adapter = adminreqadapter
                    adminreqadapter.notifyDataSetChanged()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                throw error.toException()
            }

        })
    }

    fun onRejectClickCallback(ride: Rides) {
        Toast.makeText(context, ride.rideID, Toast.LENGTH_SHORT).show()
        val database = Firebase.database.reference
        database.child("rides")
            .child(ride.rideID!!)
            .child("ridestatus")
            .setValue(classConstants.REJECT_RIDE_STATUS)

        adminreqadapter.notifyDataSetChanged()

    }

    fun onAcceptClickCallback(ride: Rides) {
        Toast.makeText(context, ride.rideID, Toast.LENGTH_SHORT).show()
        val database = Firebase.database.reference
        database.child("rides")
            .child(ride.rideID!!)
            .child("ridestatus")
            .setValue(classConstants.PICKUP_RIDE_STATUS)

        adminreqadapter.notifyDataSetChanged()

    }
}