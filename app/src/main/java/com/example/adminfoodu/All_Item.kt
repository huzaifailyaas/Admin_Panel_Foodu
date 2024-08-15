package com.example.adminfoodu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminfoodu.Adapter.Add_Item_Adapter
import com.example.adminfoodu.databinding.ActivityAllItemBinding
import com.example.adminfoodu.model.AllMenu
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class All_Item : AppCompatActivity() {
    private lateinit var database:FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference
    private  var menuItems:ArrayList<AllMenu> = ArrayList()
    private val binding: ActivityAllItemBinding by lazy {
        ActivityAllItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        databaseReference=FirebaseDatabase.getInstance().reference
        retrieveMenuItem()

        binding.backbutton.setOnClickListener{
            finish()
        }
    }

    private fun retrieveMenuItem() {
        database=FirebaseDatabase.getInstance()
        val foodRef:DatabaseReference= database.reference.child("menu")

        //Fetch data from database
        foodRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                //Clear Existing Data before populating
                menuItems.clear()

                //loop for through each food item
                for (foodSnapshot in snapshot.children){
                    val menuItem=foodSnapshot.getValue(AllMenu::class.java)
                        menuItem?.let {
                            menuItems.add(it)
                        }
                }
                setAdapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("DatabaseError", "Error: ${error.message} ")
            }

        })
    }
    private fun setAdapter() {
        val adapter = Add_Item_Adapter(this@All_Item,menuItems,databaseReference)
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.cartRecyclerView.adapter = adapter
    }
}
