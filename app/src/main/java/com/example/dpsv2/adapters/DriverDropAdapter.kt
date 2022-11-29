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


internal class DriverDropAdapter(
    private var itemlist: ArrayList<Rides>,
    private val navigate_callback : (Rides) -> Unit,
    private val drop_callback : (Rides) -> Unit
):


    RecyclerView.Adapter<DriverDropAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var usr_start_location: TextView = view.findViewById(R.id.usr_start_location)
        var usr_end_location: TextView = view.findViewById(R.id.usr_end_location)
        var usr_ride_id: TextView = view.findViewById(R.id.usr_ride_id)
        var usr_su_id: TextView = view.findViewById(R.id.usr_su_id)
        var navigate_button: Button = view.findViewById(R.id.navigate_button)
        var drop_button: Button = view.findViewById(R.id.pickup_button)

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
        holder.drop_button.text = "Drop"
        holder.drop_button.setOnClickListener{
            drop_callback(item)
        }
    }
    override fun getItemCount(): Int {
        return itemlist.size
    }
}
