package com.example.cc17mobileapplicationproject

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
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
            val logoText = findViewById<TextView>(R.id.textView8)
            val splashIcon = findViewById<ImageView>(R.id.splashIcon)

            val rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
            val textAnim = AnimationUtils.loadAnimation(this, R.anim.fade)

            logoText.startAnimation(textAnim)
            splashIcon.startAnimation(rotateAnimation)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignReg::class.java)
            startActivity(intent)
            finish()
        }, 4000)
    }
}