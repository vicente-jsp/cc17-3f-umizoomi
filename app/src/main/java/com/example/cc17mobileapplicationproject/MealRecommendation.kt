package com.example.cc17mobileapplicationproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment

class MealRecommendation : Fragment() {

    lateinit var spinnerMealRecommendations: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_meal_recommendation, container, false)

        spinnerMealRecommendations = view.findViewById(R.id.spinner_meal_recommendations)

        // Set up adapter for meal recommendations
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.meal_recommendations,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMealRecommendations.adapter = adapter

        return view
    }
}