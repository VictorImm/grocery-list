package com.example.grocerylist.dataViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grocerylist.data.GroceryDao

class GroceryViewModel(private val groceryDao: GroceryDao): ViewModel() {
    // TODO: add functionnnssss
}

class GroceryViewModelFactory(private val groceryDao: GroceryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(groceryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}