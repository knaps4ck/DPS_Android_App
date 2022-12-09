package com.example.dpsv2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.fragments.AdminDriversFragment
import com.example.dpsv2.models.Rides

private class AdminDriverAdapter(private var itemlist: ArrayList<Rides>,
                                 private val accept_callback : (Rides) -> Unit,
                                 private val reject_callback : (Rides) -> Unit,
                                 private val context : Context
):
    RecyclerView.Adapter<AdminDriverAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var driverName: TextView = view.findViewById(R.id.driver_name)
        var driverAddress: TextView = view.findViewById(R.id.driver_curr_location)
    }


        @NonNull
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDriverAdapter.MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_ride_card, parent, false)

            return MyViewHolder(itemView)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = itemlist[position]
            holder.driverName.text = item.source
            holder.driverAddress.text = item.destination
        }

        override fun getItemCount(): Int {
            return itemlist.size
        }
}