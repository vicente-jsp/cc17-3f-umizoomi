package com.example.cc17mobileapplicationproject

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cc17mobileapplicationproject.databinding.FragmentCreatemealBinding

class createmeal : Fragment() {

    private lateinit var binding: FragmentCreatemealBinding
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var foodViewModel: FoodViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCreatemealBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize ViewModel
        foodViewModel = FoodViewModel(requireActivity().application)

        // Initialize adapter and RecyclerView
        foodAdapter = FoodAdapter(emptyList()) { food ->
            foodViewModel.update(food) // Update food when checkbox state changes
        }
        binding.rvTodoItems.adapter = foodAdapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(requireContext())

        // Observe LiveData from ViewModel
        foodViewModel.allFoods.observe(viewLifecycleOwner, { foods ->
            foodAdapter.setFoods(foods)
            updateTotals(foods)
        })

        // Add food item
        binding.btnAddFood.setOnClickListener {
            val foodName = binding.etFood.text.toString()
            val caloriesInput = binding.etCalories.text.toString()
            val fatsInput = binding.etFats.text.toString()
            val proteinInput = binding.etProtein.text.toString()

            if (foodName.isNotEmpty() && caloriesInput.isNotEmpty() && fatsInput.isNotEmpty() && proteinInput.isNotEmpty()) {
                val food = Food(
                    title = foodName,
                    calories = caloriesInput.toFloat(),
                    fats = fatsInput.toFloat(),
                    protein = proteinInput.toFloat(),
                    isChecked = false
                )

                foodViewModel.insert(food)

                // Clear input fields
                binding.etFood.text.clear()
                binding.etCalories.text.clear()
                binding.etFats.text.clear()
                binding.etProtein.text.clear()
            }
        }

        // Delete selected items
        binding.btnClear.setOnClickListener {
            val selectedFoods = foodAdapter.getSelectedFoods()
            selectedFoods.forEach { food ->
                foodViewModel.delete(food)
            }
        }
    }

    private fun updateTotals(foods: List<Food>) {
        var totalCalories = 0f
        var totalFats = 0f
        var totalProtein = 0f

        for (food in foods) {
            totalCalories += food.calories
            totalFats += food.fats
            totalProtein += food.protein
        }

        binding.tvTotalCalories.text = String.format("%.2f", totalCalories)
        binding.tvTotalFats.text = String.format("%.2f g", totalFats)
        binding.tvTotalProtein.text = String.format("%.2f g", totalProtein)
    }
}