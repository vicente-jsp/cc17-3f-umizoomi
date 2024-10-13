package com.example.cc17mobileapplicationproject

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class notifications : Fragment() {

    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var notificationAdapter: NotificationAdapter
    private val notifications = mutableListOf<NotificationItem>()

    private val CHANNEL_ID = "notification_channel"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        notificationRecyclerView = view.findViewById(R.id.notificationRecyclerView)
        notificationRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        notificationAdapter = NotificationAdapter(notifications) { position ->
            deleteNotification(position)
        }
        notificationRecyclerView.adapter = notificationAdapter

        val clearAllButton: Button = view.findViewById(R.id.clearAllButton)
        clearAllButton.setOnClickListener { clearAllNotifications() }

        createNotificationChannel() // Create notification channel for Android O and above

        // Example of adding a notification
        addNotification("Test Notification", "This is a test notification.")

        return view
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notifications"
            val descriptionText = "Channel for notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT // Adjust this as needed
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addNotification(title: String, content: String) {
        val notificationId = notifications.size // Simple ID assignment
        val notificationItem = NotificationItem(notificationId, title, content)
        notifications.add(notificationItem)
        notificationAdapter.notifyItemInserted(notifications.size - 1)

        // Create the notification
        val builder = android.app.Notification.Builder(requireContext(), CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(android.app.Notification.PRIORITY_DEFAULT)

        val notificationManager = requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, builder.build())
    }

    private fun deleteNotification(position: Int) {
        notifications.removeAt(position)
        notificationAdapter.notifyItemRemoved(position)
        Toast.makeText(requireContext(), "Notification deleted", Toast.LENGTH_SHORT).show()
    }

    private fun clearAllNotifications() {
        notifications.clear()
        notificationAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), "All notifications cleared", Toast.LENGTH_SHORT).show()
    }
}