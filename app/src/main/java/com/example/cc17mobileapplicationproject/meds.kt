package com.example.cc17mobileapplicationproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class meds: Fragment() {

    private lateinit var symptomsRecyclerView: RecyclerView
    private val symptomsList = listOf(
        "Headache",
        "Nausea",
        "Fatigue",
        "Dizziness",
        "Cough",
        "Fever",
        "Sore Throat"
    )

    private val remediesMap = mapOf(
        "Headache" to listOf("Remedies","Take Ibuprofen", "Drink Water", "Rest"),
        "Nausea" to listOf("Remedies","Ginger Tea", "Rest", "Eat Crackers"),
        "Fatigue" to listOf("Remedies","Get More Sleep", "Stay Hydrated", "Take a Short Walk"),
        "Dizziness" to listOf("Remedies","Sit Down", "Stay Hydrated", "Take Deep Breaths"),
        "Cough" to listOf("Remedies","Honey and Lemon", "Stay Hydrated", "Ginger Tea"),
        "Fever" to listOf("Remedies","Take Paracetamol", "Rest", "Drink Fluids"),
        "Sore Throat" to listOf("Remedies","Gargle Salt Water", "Drink Warm Tea", "Honey")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_meds, container, false)
        symptomsRecyclerView = view.findViewById(R.id.symptomsRecyclerView1)
        symptomsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        symptomsRecyclerView.adapter = medsAdapter(symptomsList, remediesMap) { symptom, action ->
            recommendAction(symptom, action)
        }
        return view
    }

    private fun recommendAction(symptom: String, action: String) {
        if (action != "Select Action") {
            println("Selected action for $symptom: $action")
        }
    }
}