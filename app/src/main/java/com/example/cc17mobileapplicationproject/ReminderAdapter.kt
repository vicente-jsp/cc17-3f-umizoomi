package com.example.cc17mobileapplicationproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReminderAdapter(
    private val reminders: MutableList<Reminder>,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {

    class ReminderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reminderDateTimeTextView: TextView = itemView.findViewById(R.id.reminderDateTimeTextView)
        val reminderDescriptionTextView: TextView = itemView.findViewById(R.id.reminderDescriptionTextView)
        val deleteButton: Button = itemView.findViewById(R.id.deleteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.reminder_item, parent, false)
        return ReminderViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        val reminder = reminders[position]
        holder.reminderDateTimeTextView.text = reminder.dateTime
        holder.reminderDescriptionTextView.text = reminder.description

        // Set up delete button click listener
        holder.deleteButton.setOnClickListener {
            onDeleteClick(position)
        }
    }

    override fun getItemCount(): Int = reminders.size
}