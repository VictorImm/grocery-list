package com.example.grocerylist

import android.app.Application
import com.example.grocerylist.data.GroceryRoomDatabase

class GroceryApplication: Application() {
    val database: GroceryRoomDatabase by lazy {
        GroceryRoomDatabase.getDatabase(applicationContext)
    }
}