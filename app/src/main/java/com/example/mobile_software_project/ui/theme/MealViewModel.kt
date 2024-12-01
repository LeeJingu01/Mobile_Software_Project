package com.example.mobile_software_project.ui.theme
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.mobile_software_project.model.Meal

class MealViewModel:ViewModel() {
    private val _meals = mutableStateListOf<Meal>()
    val meals:List<Meal> = _meals
    fun addMeal(meal: Meal) {
        _meals.add(meal)
    }
}