package com.example.dpsv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.models.Rides

internal class AdminPrevRidesAdapter (
        private var itemlist: ArrayList<Rides>,
    ):

        RecyclerView.Adapter<AdminPrevRidesAdapter.MyViewHolder>() {
        internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            var usr_start_location: TextView = view.findViewById(R.id.usr_start_location)
            var usr_end_location: TextView = view.findViewById(R.id.usr_end_location)
            var usr_ride_id: TextView = view.findViewById(R.id.usr_ride_id)
            var usr_su_id: TextView = view.findViewById(R.id.usr_su_id)
            var accept_button: Button = view.findViewById(R.id.accept_button)
            var reject_button: Button = view.findViewById(R.id.reject_button)
            var drop_down_button: LinearLayout = view.findViewById(R.id.dropdown_layout)
        }

        @NonNull
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.admin_ride_card, parent, false)
            return MyViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            val item = itemlist[position]
            holder.usr_start_location.text = item.source
            holder.usr_end_location.text = item.destination
            holder.usr_ride_id.text = item.rideID
            holder.usr_su_id.text = item.suid
            holder.reject_button.visibility = View.GONE
            holder.accept_button.visibility = View.GONE
            holder.drop_down_button.visibility = View.GONE
        }

        override fun getItemCount(): Int {
            return itemlist.size
        }
        }