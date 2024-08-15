package com.example.adminfoodu.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminfoodu.databinding.ItemItemBinding
import com.example.adminfoodu.model.AllMenu
import com.example.adminfoodu.R
import com.google.firebase.database.DatabaseReference

class Add_Item_Adapter(
    private val context: Context,
    private val menuList: ArrayList<AllMenu>,
    databaseReference: DatabaseReference
) : RecyclerView.Adapter<Add_Item_Adapter.AddItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddItemViewHolder(binding, this)
    }

    override fun getItemCount(): Int = menuList.size

    override fun onBindViewHolder(holder: AddItemViewHolder, position: Int) {
        val menuItem = menuList[position]
        holder.bind(menuItem)
    }

    // Method to remove an item
    fun removeItem(position: Int) {
        if (position in menuList.indices) {
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }

    inner class AddItemViewHolder(
        private val binding: ItemItemBinding,
        private val adapter: Add_Item_Adapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(menuItem: AllMenu) {
            binding.apply {
                AllItemFoodName.text = menuItem.foodName
                CartItemPrice.text = menuItem.foodPrice

                // Handle image setting
                val imageUri = menuItem.foodImage
                if (imageUri != null) {
                    Glide.with(context)
                        .load(imageUri) // Assuming foodImage is a URI or URL
                        .placeholder(R.drawable.placeholder) // Placeholder image
                        .error(R.drawable.error) // Error image
                        .into(AllItemCartImage)
                } else {
                    Glide.with(context)
                        .load(R.drawable.placeholder) // Placeholder if imageUri is null
                        .into(AllItemCartImage)
                }

                DeleteButton.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        adapter.removeItem(adapterPosition)
                    }
                }
            }
        }
    }
}
