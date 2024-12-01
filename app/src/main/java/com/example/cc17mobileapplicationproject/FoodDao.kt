package com.example.cc17mobileapplicationproject

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FoodDao {

    @Insert
    suspend fun insert(food: Food)

    @Update
    suspend fun update(food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("SELECT * FROM food_table")
    fun getAllFoods(): LiveData<List<Food>>

    @Query("DELETE FROM food_table")
    suspend fun clearAll()

    @Query("SELECT * FROM food_table ORDER BY id DESC LIMIT 5")
    suspend fun getLastFiveMeals(): List<Food>
}