package com.example.agrihelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrihelp.R
import com.example.agrihelp.models.BookingDetail

class ItemsAdapter(
    private var bookings: ArrayList<BookingDetail>,
) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>()
{

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val txtServiceName: TextView = view.findViewById(R.id.txtServiceName)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtCropName: TextView = view.findViewById(R.id.txtCropName)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
        val txthours: TextView = view.findViewById(R.id.txtHours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking_details, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.txtServiceName.text = bookings[position].service_name
        holder.txtDate.text = bookings[position].bookingDate
        holder.txtCropName.text = bookings[position].cropName
        holder.txtStatus.text = bookings[position].status
        holder.txthours.text = bookings[position].actualHours.toString()
    }

    override fun getItemCount(): Int = bookings.size
}