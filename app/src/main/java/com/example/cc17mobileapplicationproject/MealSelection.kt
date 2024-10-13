package com.example.cc17mobileapplicationproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels

class MealSelection : Fragment() {
    lateinit var spinnerVegetables: Spinner
    lateinit var spinnerFruits: Spinner
    lateinit var spinnerGrains: Spinner
    lateinit var spinnerProtein: Spinner
    lateinit var spinnerDairy: Spinner

    lateinit var editTextGrams: EditText
    lateinit var btnAddFood: Button
    private val mealViewModel: MealView by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meal_selection, container, false)

        // Initialize spinners
        spinnerVegetables = view.findViewById(R.id.spinner_vegetables)
        spinnerFruits = view.findViewById(R.id.spinner_fruits)
        spinnerGrains = view.findViewById(R.id.spinner_grains)
        spinnerProtein = view.findViewById(R.id.spinner_protein)
        spinnerDairy = view.findViewById(R.id.spinner_dairy)
        editTextGrams = view.findViewById(R.id.editText_grams)
        btnAddFood = view.findViewById(R.id.btn_add_food)
        // Set up adapters for each spinner
        setupSpinner(spinnerVegetables, R.array.vegetables)
        setupSpinner(spinnerFruits, R.array.fruits)
        setupSpinner(spinnerGrains, R.array.grains)
        setupSpinner(spinnerProtein, R.array.protein_foods)
        setupSpinner(spinnerDairy, R.array.dairy)

        btnAddFood.setOnClickListener {
            val selectedFood = spinnerVegetables.selectedItem.toString()
            val grams = editTextGrams.text.toString().toIntOrNull()

            if (grams != null && grams > 0) {
                // Add food and grams to the ViewModel
                mealViewModel.addMealItem(selectedFood, grams)
                Toast.makeText(requireContext(), "Added $selectedFood - $grams grams", Toast.LENGTH_SHORT).show()
                editTextGrams.text.clear() // Clear the input field
            } else {
                Toast.makeText(requireContext(), "Please enter valid grams", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun setupSpinner(spinner: Spinner, arrayResourceId: Int) {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            arrayResourceId,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }
}