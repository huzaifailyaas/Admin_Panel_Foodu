package com.example.adminfoodu.Adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodu.databinding.DelieveryItemBinding

class DeliveryAdapter(
    private val customerNames: ArrayList<String>,
    private val payments: ArrayList<String>
) : RecyclerView.Adapter<DeliveryAdapter.DeliveryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = DelieveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryViewHolder(binding)
    }

    override fun getItemCount(): Int = customerNames.size

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        val customerName = customerNames[position]
        val payment = payments[position]
        val status = if (payment == "Received") "Received" else if (payment == "NotReceived") "NotReceived" else "Pending"
        holder.bind(customerName, payment, status)
    }

    inner class DeliveryViewHolder(private val binding: DelieveryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(customerName: String, payment: String, status: String) {
            binding.apply {
                CustomerNames.text = customerName
                StatusMoney.text = payment

                // Map status to colors
                val colorMap = mapOf(
                    "Received" to Color.GREEN,
                    "NotReceived" to Color.RED,
                    "Pending" to Color.GRAY
                )

                // Set the text color based on the status
                StatusMoney.setTextColor(colorMap[status] ?: Color.BLACK)

                // Set the background tint color or other color-related properties
                StatusColor.backgroundTintList = ColorStateList.valueOf(colorMap[status] ?: Color.BLACK)
            }
        }
    }
}