package com.example.adminfoodu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.adminfoodu.databinding.ActivitySignupBinding
import com.example.adminfoodu.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignupActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var nameOfUser: String
    private lateinit var nameofRestaurant: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference
    private val binding: ActivitySignupBinding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = Firebase.auth
        database = Firebase.database.reference

        binding.CreateAccount.setOnClickListener {
            //get text from edit text
            nameOfUser = binding.ownername.text.toString().trim()
            nameofRestaurant = binding.restaurantname.text.toString().trim()
            email = binding.email.text.toString().trim()
            password = binding.signupassword.text.toString().trim()

            if (nameOfUser.isBlank() || nameofRestaurant.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_LONG).show()
            } else {
                createAccount(email, password)
            }

        }
        binding.AlreadyAccount.setOnClickListener {
            val intent = Intent(this, LoginpageActivity::class.java)
            startActivity(intent)
        }

        val locationlist = arrayOf("lahore", "Faisalabad", "Karachi", "Multan")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationlist)
        val autoCompleteTextView = binding.listofLocation
        autoCompleteTextView.setAdapter(adapter)
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Account created Successfully", Toast.LENGTH_SHORT).show()
                saveUserData()
                val intent = Intent(this, LoginpageActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Account Creation Failed", Toast.LENGTH_SHORT).show()
                Log.d("Account", "create account: Failure", task.exception)
            }
        }
    }

    //save data into database
    private fun saveUserData() {
        //get text from edit text
        nameOfUser = binding.ownername.text.toString().trim()
        nameofRestaurant = binding.restaurantname.text.toString().trim()
        email = binding.email.text.toString().trim()
        password = binding.signupassword.text.toString().trim()

        val user = UserModel(nameOfUser, nameofRestaurant, email, password)
        val userId = FirebaseAuth.getInstance().currentUser!!.uid
        //save userdata firebase databse
        database.child("user").child(userId).setValue(user)

    }

}