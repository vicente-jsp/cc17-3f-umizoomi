package com.example.cc17mobileapplicationproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FoodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: FoodRepository
    val allFoods: LiveData<List<Food>>

    init {
        val foodDao = FoodDatabase.getDatabase(application).foodDao()
        repository = FoodRepository(foodDao)
        allFoods = repository.allFoods
    }

    fun insert(food: Food) {
        viewModelScope.launch {
            repository.insert(food)
        }
    }

    fun update(food: Food) {
        viewModelScope.launch {
            repository.update(food)
        }
    }

    fun delete(food: Food) {
        viewModelScope.launch {
            repository.delete(food)
        }
    }

    fun clearAll() {
        viewModelScope.launch {
            repository.clearAll()
        }
    }
}