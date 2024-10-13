package com.example.cc17mobileapplicationproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels


class createmeal : Fragment() {

    lateinit var mealTypeGroup: RadioGroup
    lateinit var btnProceed: Button
    private val mealViewModel: MealView by activityViewModels()
    lateinit var listViewMealItems: ListView
    lateinit var adapter: ArrayAdapter<String>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_createmeal, container, false)

        mealTypeGroup = view.findViewById(R.id.meal_type_group)
        btnProceed = view.findViewById(R.id.btn_proceed)

        listViewMealItems = view.findViewById(R.id.listView_meal_items)

        mealViewModel.mealItems.observe(viewLifecycleOwner) { mealItems ->
            val displayList = mealItems.map { "${it.first}: ${it.second} grams" }
            adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, displayList)
            listViewMealItems.adapter = adapter
        }

        // Handle deletion of items
        listViewMealItems.setOnItemClickListener { _, _, position, _ ->
            // Remove the selected item
            mealViewModel.removeMealItem(position)
            Toast.makeText(requireContext(), "Item removed", Toast.LENGTH_SHORT).show()
        }

        btnProceed.setOnClickListener {
            when (mealTypeGroup.checkedRadioButtonId) {
                R.id.rb_breakfast, R.id.rb_lunch, R.id.rb_dinner -> {
                    // Navigate to the FoodSelectionFragment
                    openFragment(MealSelection())
                }
                R.id.rb_meal_recommendations -> {
                    // Navigate to the MealRecommendationFragment
                    openFragment(MealRecommendation())
                }
                else -> {
                    Toast.makeText(requireContext(), "Please select a meal type", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return view
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main, fragment) // Assuming fragment_container is your container ID in main layout
        transaction.addToBackStack(null) // Adds to the back stack, so users can navigate back
        transaction.commit()
    }
}