package com.example.cc17mobileapplicationproject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cc17mobileapplicationproject.databinding.ItemTodoBinding

class FoodAdapter(
    private var foods: List<Food>,
    private val onFoodCheckedChange: (Food) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    class FoodViewHolder(
        private val binding: ItemTodoBinding,
        private val onFoodCheckedChange: (Food) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(food: Food) {
            binding.tvFood.text = food.title
            binding.tvCalories.text = "Calories: %.2f".format(food.calories)
            binding.tvFats.text = "Fats: %.2f g".format(food.fats)
            binding.tvProtein.text = "Protein: %.2f g".format(food.protein)
            binding.cbDone.isChecked = food.isChecked

            binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
                food.isChecked = isChecked
                onFoodCheckedChange(food)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding, onFoodCheckedChange)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foods[position])
    }

    override fun getItemCount(): Int = foods.size

    fun setFoods(newFoods: List<Food>) {
        foods = newFoods
        notifyDataSetChanged()
    }

    fun getSelectedFoods(): List<Food> {
        return foods.filter { it.isChecked }
    }
}