package com.example.mobile_software_project.model
import kotlin.random.Random
import androidx.annotation.DrawableRes

data class Affirmation(
    @DrawableRes val imageRes:Int,
    val location:String
)

data class FoodDetail(
    val name: String,
    val review: String,
    val date: String,
    val mealType: String,
    val cost: Int
)

data class Meal(
    val name: String,
    val date: String,
    val type: String, // 조식, 중식, 석식, 간식 등
    val review: String,
    val cost: Int,
    val calories: Int = Random.nextInt(100, 501), // 100~500 랜덤 칼로리
    val affirmation: Affirmation
)

