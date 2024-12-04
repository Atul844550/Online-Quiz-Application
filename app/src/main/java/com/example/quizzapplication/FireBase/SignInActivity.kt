package com.example.quizzapplication.FireBase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckedTextView
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.quizzapplication.MainActivity
import com.example.quizzapplication.R
import com.example.quizzapplication.StudentDetailDashBoard
import com.example.quizzapplication.objects.Base
import com.google.firebase.auth.FirebaseAuth
import kotlin.io.encoding.Base64

class  SignInActivity : AppCompatActivity() {


    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var newregister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)


        auth = FirebaseAuth.getInstance()
        loginButton = findViewById(R.id.loginButton)
        newregister = findViewById(R.id.newregister)


        loginButton.setOnClickListener {
            val email = findViewById<EditText>(R.id.username).text.toString().trim()
            val password = findViewById<EditText>(R.id.password).text.toString().trim()


            if (email.isEmpty()) {
                findViewById<EditText>(R.id.username).error = "Email is required"
                findViewById<EditText>(R.id.username).requestFocus()
                return@setOnClickListener
            }

            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                findViewById<EditText>(R.id.username).error = "Please enter a valid email"
                findViewById<EditText>(R.id.username).requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                findViewById<EditText>(R.id.password).error = "Password is required"
                findViewById<EditText>(R.id.password).requestFocus()
                return@setOnClickListener
            }

            signInUser(email, password)
        }


        newregister.setOnClickListener {

            startActivity(Intent(this, SignUpActivity::class.java))
        }
    }

    // Method to sign in the user
    private fun signInUser(email: String, password: String) {

        val pb = Base.showProgressBar(this)
        auth.signInWithEmailAndPassword(email, password)

            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, navigate to new activity
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, StudentDetailDashBoard::class.java)
                    intent.putExtra("userEmail ", email)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(
                        this,
                        "Login failed:",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }
}