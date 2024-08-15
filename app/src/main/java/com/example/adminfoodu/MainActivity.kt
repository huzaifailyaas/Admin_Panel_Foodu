package com.example.adminfoodu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminfoodu.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.AddMenu.setOnClickListener {
            val intent = Intent(this,AddItemActivity::class.java)
            startActivity(intent)
        }

        binding.AdminItemMenu.setOnClickListener {
            val intent1 = Intent(this, All_Item::class.java)
            startActivity(intent1)
        }

        binding.AdminProfile.setOnClickListener{
            val intent=Intent(this,ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.CreateNewUser.setOnClickListener{
            val intent=Intent(this,CreateNewUser::class.java)
            startActivity(intent)
        }

        binding.dipatch.setOnClickListener{
            val intent=Intent(this,OutForDelievery::class.java)
            startActivity(intent)
        }

        binding.PendingOrderTextView.setOnClickListener{
            val intent=Intent(this,PendingOrders::class.java)
            startActivity(intent)
        }

    }
}
