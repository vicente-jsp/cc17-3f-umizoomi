package com.example.cc17mobileapplicationproject

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cc17mobileapplicationproject.databinding.FragmentDashboardBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlinx.coroutines.launch
import kotlin.math.roundToInt



class Dashboard : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: FragmentDashboardBinding
    private lateinit var lineChart: LineChart
    private lateinit var caloriesAverage: TextView
    private lateinit var proteinAverage: TextView
    private lateinit var fatAverage: TextView
    private lateinit var foodDao: FoodDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val db = FoodDatabase.getDatabase(requireContext())
        foodDao = db.foodDao()

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        caloriesAverage = view.findViewById(R.id.caloriesAverage)
        proteinAverage = view.findViewById(R.id.proteinAverage)
        fatAverage = view.findViewById(R.id.fatAverage)
        lineChart = view.findViewById(R.id.lineGraph)

        loadChartData()

        sharedPreferences = requireContext().getSharedPreferences("BMI_PREFS", Context.MODE_PRIVATE)

        // Load saved weight and height
        val savedWeight = sharedPreferences.getFloat("WEIGHT", 0f)
        val savedHeight = sharedPreferences.getFloat("HEIGHT", 0f)

        if (savedWeight != 0f) binding.etWeight.setText(savedWeight.toString())
        if (savedHeight != 0f) binding.etHeight.setText(savedHeight.toString())

        // Handle button click
        binding.btnUpdate.setOnClickListener {
            val weightInput = binding.etWeight.text.toString()
            val heightInput = binding.etHeight.text.toString()

            if (weightInput.isNotEmpty() && heightInput.isNotEmpty()) {
                val weight = weightInput.toFloat()
                val height = heightInput.toFloat()

                saveData(weight, height)

                val bmi = calculateBMI(weight, height)

                val bmiCategory = getBMICategory(bmi)

                binding.tvBmi.text = String.format("BMI: %.2f", bmi)
                binding.tvBmiCategory.text = "Category: $bmiCategory"
            }
        }
    }

    private fun saveData(weight: Float, height: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat("WEIGHT", weight)
        editor.putFloat("HEIGHT", height)
        editor.apply()
    }

    private fun calculateBMI(weight: Float, height: Float): Float {
        return weight / (height * height)
    }

    private fun getBMICategory(bmi: Float): String {
        return when {
            bmi < 18.5 -> "Underweight"
            bmi in 18.5..24.9 -> "Normal weight"
            bmi in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun loadChartData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val meals = foodDao.getLastFiveMeals()

            if (meals.isNotEmpty()) {
                val calorieEntries = ArrayList<Entry>()
                val proteinEntries = ArrayList<Entry>()
                val fatEntries = ArrayList<Entry>()

                meals.reversed().forEachIndexed { index, meal ->
                    calorieEntries.add(Entry(index.toFloat(), meal.calories))
                    proteinEntries.add(Entry(index.toFloat(), meal.protein))
                    fatEntries.add(Entry(index.toFloat(), meal.fats))
                }

                val avgCalories = meals.map { it.calories }.average().roundToInt()
                val avgProtein = meals.map { it.protein }.average().roundToInt()
                val avgFat = meals.map { it.fats }.average().roundToInt()

                updateUI(avgCalories, avgProtein, avgFat, calorieEntries, proteinEntries, fatEntries, meals.size)
            }
        }
    }

    private fun updateUI(
        avgCalories: Int,
        avgProtein: Int,
        avgFat: Int,
        calorieEntries: List<Entry>,
        proteinEntries: List<Entry>,
        fatEntries: List<Entry>,
        mealCount: Int
    ) {
        caloriesAverage.text = "Average Calories: $avgCalories"
        proteinAverage.text = "Average Protein: $avgProtein"
        fatAverage.text = "Average Fat: $avgFat"

        val calorieDataSet = LineDataSet(calorieEntries, "Calories").apply { color = Color.GREEN }
        val proteinDataSet = LineDataSet(proteinEntries, "Protein").apply { color = Color.RED }
        val fatDataSet = LineDataSet(fatEntries, "Fat").apply { color = Color.BLUE }

        val lineData = LineData(calorieDataSet, proteinDataSet, fatDataSet)
        lineChart.data = lineData

        val xAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.valueFormatter = IndexAxisValueFormatter((1..mealCount).map { it.toString() })
        xAxis.granularity = 1f

        lineChart.axisLeft.axisMinimum = 0f
        lineChart.axisRight.isEnabled = false

        lineChart.description = Description().apply { text = "Macros by Meal" }
        lineChart.invalidate()
    }
}