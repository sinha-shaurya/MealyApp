package com.example.mealy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.MealRepository

class MealViewModelFactory(val repository: MealRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java))
            return MealViewModel(repository) as T
        throw IllegalArgumentException("No viewmodel")
    }
}