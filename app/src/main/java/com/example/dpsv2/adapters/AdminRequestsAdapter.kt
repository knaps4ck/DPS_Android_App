package com.example.dpsv2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings.Global.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.dpsv2.R
import com.example.dpsv2.models.Rides

internal class AdminRequestsAdapter (
    private var itemlist: ArrayList<Rides>,
    private val accept_callback : (Rides) -> Unit,
    private val reject_callback : (Rides) -> Unit,
    private val context : Context
):

    RecyclerView.Adapter<AdminRequestsAdapter.MyViewHolder>() {
    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var usr_start_location: TextView = view.findViewById(R.id.usr_start_location)
        var usr_end_location: TextView = view.findViewById(R.id.usr_end_location)
        var usr_ride_id: TextView = view.findViewById(R.id.usr_ride_id)
        var usr_su_id: TextView = view.findViewById(R.id.usr_su_id)
        var accept_button: Button = view.findViewById(R.id.accept_button)
        var reject_button: Button = view.findViewById(R.id.reject_button)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminRequestsAdapter.MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.admin_ride_card, parent, false)

        return MyViewHolder(itemView)
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = itemlist[position]
        holder.usr_start_location.text = item.source
        holder.usr_end_location.text = item.destination
        holder.usr_ride_id.text = item.rideID
        holder.usr_su_id.text = item.suid
        holder.accept_button.text = "accept"
        holder.accept_button.setOnClickListener{
            accept_callback(item)
        }
        holder.reject_button.text = "reject"
        holder.reject_button.setOnClickListener{
            reject_callback(item)
        }
        holder.reject_button.visibility = View.GONE
        holder.accept_button.visibility = View.GONE

        val driverList = arrayOf("Select driver:", "Driver A", "Driver B", "Driver C")

        // access the spinner

        if (holder.spinner != null) {
            val adapter = ArrayAdapter( context,
                R.layout.admin_drop_down_list, driverList
            )
            holder.spinner.adapter = adapter

            holder.spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    if (position > 0){
                        holder.reject_button.visibility = View.VISIBLE
                        holder.accept_button.visibility = View.VISIBLE
                        Toast.makeText(
                            context, driverList[position], Toast.LENGTH_SHORT
                        ).show()
                    }
                    else{
                        holder.reject_button.visibility = View.GONE
                        holder.accept_button.visibility = View.GONE
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }
}