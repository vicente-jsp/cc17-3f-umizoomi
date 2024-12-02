package com.example.cc17mobileapplicationproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cc17mobileapplicationproject.databinding.ActivityCredentialsBinding

class Credentials : AppCompatActivity() {

    private lateinit var binding: ActivityCredentialsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityCredentialsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        loadUserData()


        binding.buttonCont.setOnClickListener {
            saveUserData()
            val intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        saveUserData()
    }

    private fun saveUserData() {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()


        editor.putString("Username", binding.usertext.text.toString())
        editor.putString("Weight", binding.kg.text.toString())
        editor.putString("Height", binding.meters.text.toString())
        editor.putString("Sex", binding.malefemale.text.toString())
        editor.putString("Email", binding.example.text.toString())


        editor.apply()
    }

    private fun loadUserData() {
        val sharedPreferences = getSharedPreferences("UserData", Context.MODE_PRIVATE)


        binding.usertext.setText(sharedPreferences.getString("Username", ""))
        binding.kg.setText(sharedPreferences.getString("Weight", ""))
        binding.meters.setText(sharedPreferences.getString("Height", ""))
        binding.malefemale.setText(sharedPreferences.getString("Sex", ""))
        binding.example.setText(sharedPreferences.getString("Email", ""))
    }
}