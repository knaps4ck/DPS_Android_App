package com.example.dpsv2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.models.Rides

internal class DriverPickupAdapter(
    private var itemlist: ArrayList<Rides>,
    private val navigate_callback : (Rides) -> Unit,
    private val pickup_callback : (Rides) -> Unit
):


    RecyclerView.Adapter<DriverPickupAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var usr_start_location: TextView = view.findViewById(R.id.usr_start_location)
        var usr_end_location: TextView = view.findViewById(R.id.usr_end_location)
        var usr_ride_id: TextView = view.findViewById(R.id.usr_ride_id)
        var usr_su_id: TextView = view.findViewById(R.id.usr_su_id)
        var navigate_button: Button = view.findViewById(R.id.navigate_button)
        var pickup_button: Button = view.findViewById(R.id.pickup_button)

    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.driver_ride_card, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemlist[position]
        holder.usr_start_location.text = item.source
        holder.usr_end_location.text = item.destination
        holder.usr_ride_id.text = item.rideID
        holder.usr_su_id.text = item.suid
        holder.navigate_button.setOnClickListener{
            navigate_callback(item)
        }
        holder.pickup_button.text = "Pickup"
        holder.pickup_button.setOnClickListener{
            pickup_callback(item)
        }
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}

