package com.example.cc17mobileapplicationproject

import androidx.lifecycle.LiveData

class FoodRepository(private val foodDao: FoodDao) {

    val allFoods: LiveData<List<Food>> = foodDao.getAllFoods()

    suspend fun insert(food: Food) {
        foodDao.insert(food)
    }

    suspend fun update(food: Food) {
        foodDao.update(food)
    }

    suspend fun delete(food: Food) {
        foodDao.delete(food)
    }

    suspend fun clearAll() {
        foodDao.clearAll()
    }
}