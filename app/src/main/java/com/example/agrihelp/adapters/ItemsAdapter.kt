package com.example.agrihelp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agrihelp.R

class ItemsAdapter(private val items: Array<String>) : RecyclerView.Adapter<ItemsAdapter.ViewHolder>()
{
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val txtServiceName: TextView = view.findViewById(R.id.txtServiceName)
        val txtDate: TextView = view.findViewById(R.id.txtDate)
        val txtRentalPrice: TextView = view.findViewById(R.id.txtRentalPrice)
        val txtCropName: TextView = view.findViewById(R.id.txtCropName)
        val txtStatus: TextView = view.findViewById(R.id.txtStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.booking_details, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        holder.txtServiceName.text = items[position]
        holder.txtDate.text = items[position]
        holder.txtRentalPrice.text = items[position]
        holder.txtCropName.text = items[position]
        holder.txtStatus.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}