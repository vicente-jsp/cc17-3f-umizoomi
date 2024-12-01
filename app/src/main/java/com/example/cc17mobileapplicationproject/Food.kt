package com.example.cc17mobileapplicationproject

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "food_table")
data class Food(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val calories: Float,
    val fats: Float,
    val protein: Float,
    var isChecked: Boolean
)

