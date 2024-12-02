package com.example.cc17mobileapplicationproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back Button Logic
        val backButton = findViewById<Button>(R.id.buttonbacksett)
        backButton.setOnClickListener {
            finish()  // Simply close the Settings activity
        }

        // Push Notification Button Logic
        val pushNotifButton = findViewById<Button>(R.id.PushNotif)
        pushNotifButton.setOnClickListener {
            showPushNotificationPrompt()
        }

        // Change Password Button Logic
        val changePassButton = findViewById<Button>(R.id.changepass)
        changePassButton.setOnClickListener {
            showChangePasswordDialog()
        }

        // Logout Button Logic
        val logoutButton = findViewById<Button>(R.id.btnlogout)  // Make sure the ID matches your XML
        logoutButton.setOnClickListener {
            val intent = Intent(this, Signin_Activity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()  // Close current activity to prevent back navigation
        }
    }

    private fun showPushNotificationPrompt() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Push Notifications")
        builder.setMessage("Do you want to turn on push notifications?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            // No action is taken when 'Yes' is clicked, dialog will close automatically
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            // No action is taken when 'Cancel' is clicked, dialog will close automatically
        }
        builder.create().show()
    }

    private fun showChangePasswordDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_change_password, null)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Change Password")
        builder.setView(dialogView)

        builder.setPositiveButton("Save") { dialog, _ ->
            showConfirmationDialog()
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.cancel()
        }

        builder.create().show()
    }

    private fun showConfirmationDialog() {
        val confirmationBuilder = AlertDialog.Builder(this)
        confirmationBuilder.setMessage("Password changed.")
        confirmationBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        confirmationBuilder.create().show()
    }
}
