package com.example.cc17mobileapplicationproject

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import android.widget.CalendarView
import com.example.cc17mobileapplicationproject.databinding.FragmentCalendarBinding

class calendar : Fragment() {

    private lateinit var calendarView: CalendarView
    private lateinit var textViewDate: TextView
    private lateinit var buttonAddReminder: Button
    private lateinit var reminderRecyclerView: RecyclerView

    // List to store reminders
    private val reminders = mutableListOf<Reminder>()
    private lateinit var reminderAdapter: ReminderAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)


        // Initialize views
        calendarView = view.findViewById(R.id.calendarMismo)
        textViewDate = view.findViewById(R.id.textSelDate)
        buttonAddReminder = view.findViewById(R.id.buttonAdd)
        reminderRecyclerView = view.findViewById(R.id.reminderRecycler)

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val currentDate = sdf.format(Date())
        textViewDate.text = currentDate

        // Set up RecyclerView
        reminderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        reminderAdapter = ReminderAdapter(reminders) { position ->
            reminders.removeAt(position)
            reminderAdapter.notifyItemRemoved(position)
        }
        reminderRecyclerView.adapter = reminderAdapter

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = String.format("%02d/%02d/%d", dayOfMonth, month + 1, year)
            textViewDate.text = selectedDate
        }

        buttonAddReminder.setOnClickListener {
            val selectedDate = textViewDate.text.toString()
            showTimePickerDialog(selectedDate)
        }
        return view

    }

    private fun showTimePickerDialog(selectedDate: String) {
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            val dateTime = "Reminder for $selectedDate at $selectedTime"

            // Show dialog to enter reminder description
            showDescriptionDialog(dateTime)

        }, hour, minute, true)

        timePickerDialog.show()
    }

    private fun showDescriptionDialog(dateTime: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Enter Reminder Description")

        val input = EditText(requireContext())
        input.hint = "Description"
        builder.setView(input)

        builder.setPositiveButton("OK") { _, _ ->
            val description = input.text.toString()
            val reminder = Reminder(dateTime, description)
            reminders.add(reminder)
            reminderAdapter.notifyItemInserted(reminders.size - 1)
            Toast.makeText(requireContext(), "Reminder added", Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


}
}