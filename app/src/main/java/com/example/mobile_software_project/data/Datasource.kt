package com.example.mobile_software_project.data

import com.example.mobile_software_project.R
import com.example.mobile_software_project.model.Affirmation
import com.example.mobile_software_project.model.Meal

object Datasource {
    val affirmation = listOf(
        Affirmation(R.drawable.cake,"기숙사"),
        Affirmation(R.drawable.coffee,"기숙사"),
        Affirmation(  R.drawable.madeleine,"상록원 1층"),
        Affirmation( R.drawable.icetea,"상록원 1층"),
        Affirmation( R.drawable.curtlet_2,"상록원 1층"),
        Affirmation(  R.drawable.dumpling_1,"상록원 2층"),
        Affirmation(  R.drawable.gukbap_2,"상록원 2층"),
        Affirmation( R.drawable.lunch_2,"상록원 2층"),
        Affirmation( R.drawable.noodle_1,"기숙사"),
        Affirmation( R.drawable.potrice_1,"상록원 1층"),
        Affirmation( R.drawable.ramen_2,"상록원 2층"),
        Affirmation( R.drawable.udon_1,"상록원 1층"),
    )
    fun getAffirmationsByLocation(location: String): List<Affirmation> {
        return affirmation.filter { it.location == location }
    }
}
object MealDataStore {
    private val meals = mutableListOf<Meal>()

    fun addMeal(meal: Meal) {
        meals.add(meal)
    }

    fun getMeals(): List<Meal> {
        return meals
    }
}