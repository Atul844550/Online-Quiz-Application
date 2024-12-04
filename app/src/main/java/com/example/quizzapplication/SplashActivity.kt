package com.example.quizzapplication

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class SplashActivity : AppCompatActivity() {

    private lateinit var imageview: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        imageview = findViewById(R.id.splash_imageView)


        Handler(Looper.getMainLooper()).postDelayed(
            {
                val i = Intent(this, WelcomeActivity::class.java)
                startActivity(i)
                finish()
            }, 5000
        )
    }
}