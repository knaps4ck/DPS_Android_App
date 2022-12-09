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

internal class AdminDriverAdapter(private var driverList: ArrayList<String>,
                                  private var driverAddressList: Array<String>

):
    RecyclerView.Adapter<AdminDriverAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var driverName: TextView = view.findViewById(R.id.driver_name)
        var driverAddress: TextView = view.findViewById(R.id.driver_curr_location)
    }


        @NonNull
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminDriverAdapter.MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_driver_card, parent, false)

            return MyViewHolder(itemView)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            holder.driverName.text = driverAddressList[position]
            holder.driverAddress.text =  driverList[position]
        }

        override fun getItemCount(): Int {
            return driverList.size
        }
}