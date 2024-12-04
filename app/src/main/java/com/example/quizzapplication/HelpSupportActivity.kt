package com.example.quizzapplication

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HelpSupportActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    lateinit var button: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_help_support)

        button = findViewById(R.id.button)

        button.setOnClickListener {

            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}