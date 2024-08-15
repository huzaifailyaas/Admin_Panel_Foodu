package com.example.adminfoodu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodu.Adapter.DeliveryAdapter
import com.example.adminfoodu.databinding.ActivityOutForDelieveryBinding

class OutForDelievery : AppCompatActivity() {
    private val binding: ActivityOutForDelieveryBinding by lazy {
        ActivityOutForDelieveryBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root) // Use binding.root here

        binding.backbutton.setOnClickListener{
            finish()
        }

        val customerName = arrayListOf(
            "John Doe",
            "Jane Smith",
            "Mike Johnson"
        )

        val moneyStatus = arrayListOf(
            "Received",
            "NotReceived",
            "Pending"
        )

        val adapter = DeliveryAdapter(customerName, moneyStatus)

        binding.DeliveryRecyclerView.adapter = adapter
        binding.DeliveryRecyclerView.layoutManager = LinearLayoutManager(this)
    }
}
