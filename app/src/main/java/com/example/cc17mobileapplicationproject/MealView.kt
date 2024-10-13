package com.example.cc17mobileapplicationproject

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MealView : ViewModel() {

    // List to store food items and grams
    private val _mealItems = MutableLiveData<MutableList<Pair<String, Int>>>()
    val mealItems: LiveData<MutableList<Pair<String, Int>>>
        get() = _mealItems

    init {
        _mealItems.value = mutableListOf()
    }

    // Add a new food item and grams to the list
    fun addMealItem(food: String, grams: Int) {
        _mealItems.value?.add(Pair(food, grams))
        _mealItems.value = _mealItems.value // To trigger observers
    }

    // Remove a food item from the list
    fun removeMealItem(index: Int) {
        _mealItems.value?.removeAt(index)
        _mealItems.value = _mealItems.value // To trigger observers
    }
}