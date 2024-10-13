package com.example.cc17mobileapplicationproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class medsAdapter(
    private val symptoms: List<String>,
    private val remedies: Map<String, List<String>>,
    private val onRemedySelected: (String, String) -> Unit
) : RecyclerView.Adapter<medsAdapter.SymptomsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_symptom, parent, false)
        return SymptomsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SymptomsViewHolder, position: Int) {
        val symptom = symptoms[position]
        holder.bind(symptom, remedies[symptom] ?: emptyList(), onRemedySelected)
    }

    override fun getItemCount(): Int {
        return symptoms.size
    }

    inner class SymptomsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val symptomTextView: TextView = itemView.findViewById(R.id.symptomTextView)
        private val remedySpinner: Spinner = itemView.findViewById(R.id.remedySpinner)

        fun bind(symptom: String, remediesList: List<String>, onRemedySelected: (String, String) -> Unit) {
            symptomTextView.text = symptom

            // Create an ArrayAdapter for the Spinner
            val adapter = ArrayAdapter(itemView.context, android.R.layout.simple_spinner_item, remediesList)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            remedySpinner.adapter = adapter

            // Set a listener for the Spinner
            remedySpinner.setOnItemSelectedListener(object : android.widget.AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: android.widget.AdapterView<*>, view: View?, position: Int, id: Long) {
                    val selectedAction = remediesList[position]
                    onRemedySelected(symptom, selectedAction)
                }

                override fun onNothingSelected(parent: android.widget.AdapterView<*>) {
                    // Do nothing
                }
            })
        }
    }
}