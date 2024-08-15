package com.example.adminfoodu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodu.Adapter.DeliveryAdapter
import com.example.adminfoodu.Adapter.PendingOrdersAdapter
import com.example.adminfoodu.databinding.ActivityPendingOrdersBinding
import com.example.adminfoodu.databinding.PendingOrdersItemBinding

class PendingOrders : AppCompatActivity() {
    private lateinit var binding: ActivityPendingOrdersBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPendingOrdersBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.backbutton.setOnClickListener{
            finish()
        }

        val OrderedcustomerName = arrayListOf(
            "John Doe",
            "Jane Smith",
            "Mike Johnson"
        )

        val OrderedQuantity = arrayListOf(
            "4",
            "5",
            "2"
        )

        val OrderedFoodImage = arrayListOf(
            R.drawable.banner11,
            R.drawable.menu22,
            R.drawable.menu44
        )

        val adapter = PendingOrdersAdapter(OrderedcustomerName, OrderedQuantity,OrderedFoodImage,this)

        binding.PendingRecycleView.adapter = adapter
        binding.PendingRecycleView.layoutManager = LinearLayoutManager(this)

    }
}