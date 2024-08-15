package com.example.adminfoodu

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminfoodu.databinding.ActivityAddItemBinding
import com.example.adminfoodu.model.AllMenu
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddItemActivity : AppCompatActivity() {

    private lateinit var foodName: String
    private lateinit var foodPrice: String
    private lateinit var foodDescription: String
    private var foodImageUri: Uri? = null
    private lateinit var foodIngredient: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddItemBinding by lazy {
        ActivityAddItemBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.addItem.setOnClickListener {
            foodName = binding.foodName.toString().trim()
            foodPrice = binding.foodPrice.toString().trim()
            foodDescription = binding.description.toString().trim()
            foodIngredient = binding.ingredients.toString().trim()

            if (!(foodName.isBlank() || foodPrice.isBlank() || foodDescription.isBlank() || foodIngredient.isBlank())) {
                uploadData()
                Toast.makeText(this, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
            binding.selectimage.setOnClickListener {
                pickImage.launch("image/*")
            }
        }

        binding.backbutton.setOnClickListener {
            finish()
        }
    }

    private fun uploadData() {

        //get refrence to "Menu" node in the database
        val menuRef = database.getReference("menu")
        //Generate new key for the new menu item
        val newItemKey = menuRef.push().key

        if (foodImageUri != null) {

            val storageRef = FirebaseStorage.getInstance().reference
            val imageRef = storageRef.child("menu_images/${newItemKey}.jpg")
            val uploadRef = imageRef.putFile(foodImageUri!!)

            uploadRef.addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { downloadurl ->

                    val newitem = AllMenu(
                        foodName = foodName,
                        foodPrice = foodPrice,
                        foodDescription = foodDescription,
                        foodIngredient = foodIngredient,
                        foodImage = downloadurl.toString(),

                        )

                    newItemKey?.let { key ->
                        menuRef.child(key).setValue(newitem).addOnSuccessListener {
                            Toast.makeText(this, "Data Upload Successfully", Toast.LENGTH_SHORT)
                                .show()
                        }
                            .addOnFailureListener {
                                Toast.makeText(this, "Data Upload Failed", Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }

                }

            }
                .addOnFailureListener {
                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()
                }


        } else {
            Toast.makeText(this, "Please Select Image", Toast.LENGTH_SHORT).show()
        }

    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            binding.selectedimage.setImageURI(uri)
            foodImageUri = uri
        } else {
            Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
        }
    }
}
