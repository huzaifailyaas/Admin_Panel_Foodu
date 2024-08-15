package com.example.adminfoodu.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.adminfoodu.databinding.PendingOrdersItemBinding

class PendingOrdersAdapter(
    private val CustomerName:ArrayList<String>,
    private val Quantity:ArrayList<String>,
    private val PendingImage:ArrayList<Int>,
    private val context:Context
    ):
    RecyclerView.Adapter<PendingOrdersAdapter.PendingOrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingOrdersViewHolder {
        val binding = PendingOrdersItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return PendingOrdersViewHolder(binding)
    }


    override fun onBindViewHolder(holder: PendingOrdersViewHolder, position: Int) {
       holder.bind(position)
    }

    override fun getItemCount(): Int = CustomerName.size


    inner class PendingOrdersViewHolder(private val binding:PendingOrdersItemBinding):RecyclerView.ViewHolder(binding.root) {
        private var isAccepted=false
        fun bind(position: Int) {
            binding.apply {
                pendingcustomername.text=CustomerName[position]
                pendingOrderQuantity.text=Quantity[position]
                pendingorderimage.setImageResource(PendingImage[position])

                acceptbutton.apply {
                    if(!isAccepted){
                        text="Accept"
                    }else{
                        text="Dispatch"
                    }
                    setOnClickListener {
                        if (!isAccepted){
                            text="Dispatch"
                            isAccepted=true
                            showToast("Order Accepted")
                        }else{

                            CustomerName.removeAt(adapterPosition)
                            notifyItemRemoved(adapterPosition)
                            showToast("Order Dispatched")

                        }
                    }
                }

            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }
}