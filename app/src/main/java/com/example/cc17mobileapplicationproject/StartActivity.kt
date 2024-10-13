package com.example.cc17mobileapplicationproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_start)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

            val splashIcon = findViewById<ImageView>(R.id.splashIcon)

            // Load the rotate animation
            val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)

            // Start the animation on the splash icon
            splashIcon.startAnimation(rotateAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignReg::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}