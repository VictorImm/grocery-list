package com.example.grocerylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.SavedItemAdapter
import com.example.grocerylist.adapter.TypeAdapter
import com.example.grocerylist.data.Item
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.GroceryViewModelFactory
import com.example.grocerylist.dataViewModel.SavedItemViewModel
import com.example.grocerylist.dataViewModel.SavedItemViewModelFactory
import com.example.grocerylist.databinding.ActivityGrocerySavedBinding
import java.lang.reflect.Type

class GrocerySaved : AppCompatActivity() {

    private lateinit var binding: ActivityGrocerySavedBinding

    // widget
    private lateinit var rvSaved: RecyclerView

    private val groceryItemViewModel: GroceryViewModel by viewModels {
        GroceryViewModelFactory(
            (application as GroceryApplication).database.groceryDao()
        )
    }

    private val savedItemViewModel: SavedItemViewModel by viewModels {
        SavedItemViewModelFactory(
            (application as GroceryApplication).database.itemDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.title = "Saved Items"

        super.onCreate(savedInstanceState)

        binding = ActivityGrocerySavedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvSaved = binding.rvSaved
        rvSaved.setHasFixedSize(true)

        savedItemViewModel.retrieveType().observe(this) {
            showRecyclerList(it)
        }
    }

    private fun showRecyclerList(items: List<Int>) {
        rvSaved.layoutManager = LinearLayoutManager(this)
        val savedTypeAdapter: TypeAdapter =
            TypeAdapter(items, groceryItemViewModel, savedItemViewModel, 3)
        rvSaved.adapter = savedTypeAdapter
    }
}