package com.example.cc17mobileapplicationproject

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import kotlin.math.roundToInt


class Dashboard : Fragment() {

    private lateinit var lineChart: LineChart
    private lateinit var caloriesAverage: TextView
    private lateinit var proteinAverage: TextView
    private lateinit var fatAverage: TextView
    private lateinit var fiberAverage: TextView
    private lateinit var vitaminAverage: TextView
    private lateinit var button: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dashboard, container, false)
        button = view.findViewById(R.id.createMealSec)
        button.setOnClickListener {
            // Navigate to AnotherFragment
            val anotherFragment = createmeal()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, anotherFragment) // Replace 'fragment_container' with your container ID
                .commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        caloriesAverage = view.findViewById(R.id.caloriesAverage)
        proteinAverage = view.findViewById(R.id.proteinAverage)
        fatAverage = view.findViewById(R.id.fatAverage)
        fiberAverage = view.findViewById(R.id.fiberAverage)
        vitaminAverage = view.findViewById(R.id.vitaminAverage)

        lineChart = view.findViewById(R.id.lineGraph)

        setupChart()

        val textView: TextView = view.findViewById(R.id.dashNotifs)

        textView.text = "No Notifications"
    }


    private fun setupChart() {
        val calorieEntries = ArrayList<Entry>()
        val proteinEntries = ArrayList<Entry>()
        val fatEntries = ArrayList<Entry>()
        val fiberEntries = ArrayList<Entry>()
        val vitaminEntries = ArrayList<Entry>()

        // Sample data: Day 1 to Day 7
        calorieEntries.apply {
            add(Entry(0f, 200f))
            add(Entry(1f, 200f))
            add(Entry(2f, 330f))
            add(Entry(3f, 210f))
            add(Entry(4f, 400f))
            add(Entry(5f, 300f))
            add(Entry(6f, 380f))
        }

        proteinEntries.apply {
            add(Entry(0f, 150f))
            add(Entry(1f, 160f))
            add(Entry(2f, 140f))
            add(Entry(3f, 170f))
            add(Entry(4f, 180f))
            add(Entry(5f, 190f))
            add(Entry(6f, 200f))
        }

        fatEntries.apply {
            add(Entry(0f, 70f))
            add(Entry(1f, 60f))
            add(Entry(2f, 75f))
            add(Entry(3f, 65f))
            add(Entry(4f, 80f))
            add(Entry(5f, 90f))
            add(Entry(6f, 85f))
        }

        fiberEntries.apply {
            add(Entry(0f, 25f))
            add(Entry(1f, 30f))
            add(Entry(2f, 20f))
            add(Entry(3f, 22f))
            add(Entry(4f, 28f))
            add(Entry(5f, 32f))
            add(Entry(6f, 35f))
        }

        vitaminEntries.apply {
            add(Entry(0f, 10f))
            add(Entry(1f, 15f))
            add(Entry(2f, 12f))
            add(Entry(3f, 20f))
            add(Entry(4f, 18f))
            add(Entry(5f, 22f))
            add(Entry(6f, 25f))
        }
        // Calculate averages
        val avgCalories = calorieEntries.map { it.y }.average().roundToInt()
        val avgProtein = proteinEntries.map { it.y }.average().roundToInt()
        val avgFat = fatEntries.map { it.y }.average().roundToInt()
        val avgFiber = fiberEntries.map { it.y }.average().roundToInt()
        val avgVitamins = vitaminEntries.map { it.y }.average().roundToInt()

        // Display averages in the TextView elements
        caloriesAverage.text = "Average Calories:         $avgCalories"
        caloriesAverage.setTextColor(Color.rgb(204,204,0))
        proteinAverage.text =  "Average Protein:          $avgProtein"
        proteinAverage.setTextColor(Color.RED)
        fatAverage.text =      "Average Fat:                $avgFat"
        fatAverage.setTextColor(Color.rgb(222,184,135))
        fiberAverage.text =    "Average Fiber:            $avgFiber"
        fiberAverage.setTextColor(Color.GRAY)
        vitaminAverage.text =  "Average Vitamins:     $avgVitamins"
        vitaminAverage.setTextColor(Color.rgb(34,139,34))



        val calorieDataSet = LineDataSet(calorieEntries, "Calories").apply {
            color = Color.rgb(204,204,0)
        }
        val proteinDataSet = LineDataSet(proteinEntries, "Protein").apply {
            color = Color.RED
        }
        val fatDataSet = LineDataSet(fatEntries, "Fat").apply {
            color = Color.rgb(222,184,135)
        }
        val fiberDataSet = LineDataSet(fiberEntries, "Fiber").apply {
            color = Color.GRAY
        }
        val vitaminDataSet = LineDataSet(vitaminEntries, "Vitamins").apply {
            color = Color.rgb(34,139,34)
        }

        val lineData = LineData(calorieDataSet, proteinDataSet, fatDataSet, fiberDataSet, vitaminDataSet)
        lineChart.data = lineData

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        val days = arrayOf("1", "2", "3", "4", "5", "6", "7")
        xAxis.valueFormatter = IndexAxisValueFormatter(days)
        xAxis.axisMinimum = 0f
        xAxis.axisMaximum = 6f

        val yAxisLeft: YAxis = lineChart.axisLeft
        yAxisLeft.axisMinimum = 0f
        yAxisLeft.axisMaximum = 400f

        val yAxisRight: YAxis = lineChart.axisRight
        yAxisRight.isEnabled = false

        lineChart.invalidate()

        lineChart.description.isEnabled = false
    }
}