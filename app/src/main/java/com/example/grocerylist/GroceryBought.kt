package com.example.grocerylist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.TypeAdapter
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.GroceryViewModelFactory
import com.example.grocerylist.databinding.ActivityGroceryBoughtBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GroceryBought : AppCompatActivity() {

    // binding
    private lateinit var binding: ActivityGroceryBoughtBinding

    // widget
    private lateinit var rvCart: RecyclerView
    private lateinit var btnFinish: Button

    // viewModel initialization
    private val viewModel: GroceryViewModel by viewModels {
        GroceryViewModelFactory(
            (application as GroceryApplication).database.groceryDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // action bar title
        supportActionBar?.title = "Cart"

        super.onCreate(savedInstanceState)
        binding = ActivityGroceryBoughtBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // recyclevuew
        rvCart = binding.rvCart
        rvCart.setHasFixedSize(true)

        viewModel.retrieveType().observe(this) {
            showRecyclerList(it)
        }

        // button finish shopping
        btnFinish = binding.btnFinish
        btnFinish.setOnClickListener { showConfirmationDialog() }
    }

    private fun showRecyclerList(items: List<Int>) {
        rvCart.layoutManager = LinearLayoutManager(this)

        // show recycle view from list of type
        val itemAdapter = TypeAdapter(items, viewModel, 2)
        rvCart.adapter = itemAdapter
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage("Delete all shopping list?")
            .setCancelable(false)
            .setNegativeButton("No") { _, _ -> }
            .setPositiveButton("Yes") { _, _ ->
                viewModel.truncateGrocery()
                finish()
            }
            .show()
    }
}