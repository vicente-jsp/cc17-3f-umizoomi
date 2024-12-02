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
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
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
    private lateinit var totalCaloriesText: TextView
    private lateinit var totalProteinText: TextView
    private lateinit var totalFatText: TextView

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
        totalCaloriesText = view.findViewById(R.id.totalCalories)
        totalProteinText = view.findViewById(R.id.totalProtein)
        totalFatText = view.findViewById(R.id.totalFat)
        loadChartData()
        loadPieChartData()
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

        val calorieDataSet = LineDataSet(calorieEntries, "Calories%").apply { color = Color.GREEN }
        val proteinDataSet = LineDataSet(proteinEntries, "Protein%").apply { color = Color.RED }
        val fatDataSet = LineDataSet(fatEntries, "Fat%").apply { color = Color.BLUE }

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
    private fun loadPieChartData() {
        viewLifecycleOwner.lifecycleScope.launch {
            // Fetch the last 5 meals from the database
            val lastFiveMeals = foodDao.getLastFiveMeals()

            if (lastFiveMeals.isNotEmpty()) {
                // Calculate total calories, protein, and fat from the last 5 meals
                val totalCalories = lastFiveMeals.sumOf { it.calories.toDouble() }
                val totalProtein = lastFiveMeals.sumOf { it.protein.toDouble() }
                val totalFat = lastFiveMeals.sumOf { it.fats.toDouble() }

                val formattedCalories = String.format("%.2f", totalCalories)
                val formattedProtein = String.format("%.2f", totalProtein)
                val formattedFat = String.format("%.2f", totalFat)

                // Set the total values on the TextViews
                totalCaloriesText.text = "Total Calories: $formattedCalories"
                totalProteinText.text = "Total Protein: $formattedProtein"
                totalFatText.text = "Total Fat: $formattedFat"
                // Set up the Pie Chart
                setupPieChart(binding.pieChart, totalCalories.toFloat(), totalProtein.toFloat(), totalFat.toFloat())
            }
        }
    }

    private fun setupPieChart(pieChart: PieChart, totalCalories: Float, totalProtein: Float, totalFat: Float) {
        // Prepare pie chart entries for Calories, Protein, and Fat
        val entries = arrayListOf(
            PieEntry(totalCalories, "Calories%"),
            PieEntry(totalProtein, "Protein%"),
            PieEntry(totalFat, "Fat%")
        )

        // Create a PieDataSet with the entries
        val dataSet = PieDataSet(entries, "").apply {
            colors = listOf(Color.GREEN, Color.RED, Color.BLUE) // Set colors for each slice
            valueTextColor = Color.WHITE
            valueTextSize = 20f
        }

        // Set the data to the pie chart
        val pieData = PieData(dataSet)

        // Configure the Pie Chart
        pieChart.apply {
            data = pieData
            description.isEnabled = false
            setUsePercentValues(true) // Show percentage in pie chart
            isDrawHoleEnabled = true // Enable the hole in the middle of the pie chart
            setHoleColor(Color.WHITE) // Color for the hole
            setEntryLabelColor(Color.BLACK) // Set color for labels
            setEntryLabelTextSize(20f) // Set size for labels
            invalidate() // Refresh the chart
        }
    }
}