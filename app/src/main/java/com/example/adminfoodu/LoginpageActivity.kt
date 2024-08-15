package com.example.adminfoodu

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.adminfoodu.databinding.ActivityLoginpageBinding
import com.example.adminfoodu.model.UserModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

@Suppress("NAME_SHADOWING")
class LoginpageActivity : AppCompatActivity() {

    private var nameOfUser: String? = null
    private var nameofRestaurant: String? = null
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var googleSignInClient: GoogleSignInClient

    private val binding: ActivityLoginpageBinding by lazy {
        ActivityLoginpageBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()


        googleSignInClient = GoogleSignIn.getClient(this, gso)


        auth = Firebase.auth
        database = Firebase.database.reference


        binding.Login.setOnClickListener {
            // get text from edit text
            email = binding.LoginEmail.text.toString().trim()
            password = binding.LoginPassword.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                Toast.makeText(this, "Please Fill All Details", Toast.LENGTH_LONG).show()

            } else {
                createUserAccount(email, password)
            }


            binding.LoginGoogle.setOnClickListener {
                val intent = googleSignInClient.signInIntent
                launcher.launch(intent)

            }
        }
        binding.LoginNoAccount.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun createUserAccount(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show()
                val user = auth.currentUser
                UpadteUi(user)
            } else {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Create User & Login Successfully", Toast.LENGTH_SHORT)
                            .show()
                        val user = auth.currentUser
                        saveUserData()
                        UpadteUi(user)

                    } else {
                        Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        Log.d(
                            "Account",
                            "create User Account: Authentication  Failure",
                            task.exception
                        )

                    }
                }
            }
        }
    }

    private fun saveUserData() {
        // get text from edit text
        email = binding.LoginEmail.text.toString().trim()
        password = binding.LoginPassword.text.toString().trim()

        val user = UserModel(nameOfUser, nameofRestaurant, email, password)
        val userid = FirebaseAuth.getInstance().currentUser?.uid

        userid?.let {
            database.child("user").child(it).setValue(user)
        }

    }

    ///launcher for Google Sign In
    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val account: GoogleSignInAccount? = task.result
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    auth.signInWithCredential(credential).addOnCompleteListener { authtask ->
                        if (authtask.isSuccessful) {
                            Toast.makeText(
                                this, "Successfully Sign In with Google", Toast.LENGTH_SHORT).show()
                            UpadteUi(authtask.result?.user)
                            finish()
                        } else {
                            Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }else{
                Toast.makeText(this, "Google Sign-In Failed", Toast.LENGTH_SHORT).show()
            }
        }

    //For Direct land to Dashboard
    override fun onStart() {
        super.onStart()
        val currentUser=auth.currentUser
        if (currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
    private fun UpadteUi(user: FirebaseUser?) {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}