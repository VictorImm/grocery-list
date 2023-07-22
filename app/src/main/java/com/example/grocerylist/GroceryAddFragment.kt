package com.example.grocerylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.GroceryViewModelFactory
import com.example.grocerylist.databinding.FragmentGroceryAddBinding
import com.example.grocerylist.databinding.FragmentGroceryListBinding

class GroceryAddFragment : Fragment() {

    companion object {
        const val TYPE = "type"
    }
    private lateinit var typeId: String

    // binding
    private lateinit var binding: FragmentGroceryAddBinding

    // widgets
    private lateinit var inputGrocery: com.google.android.material.textfield.TextInputEditText
    private lateinit var inputQty: com.google.android.material.textfield.TextInputEditText
    private lateinit var btnAdd: Button

    // viewModel initialization
    private val viewModel: GroceryViewModel by viewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryApplication).database.groceryDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            typeId = it.getString(TYPE).toString()
        }
        Log.d("ImageButton Chosen", typeId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        inputGrocery = binding.inputGrocery
        inputQty = binding.inputQty
        btnAdd = binding.btnAdd
        btnAdd.setOnClickListener { btnClicked(typeId) }
    }

    private fun btnClicked(type: String) {
        var name: String = ""
        var qty: Int = 0

        // check if entry is filled
        if (viewModel.isEntryValid(
                0,
                inputGrocery.text.toString(),
                inputQty.text.toString())
        ) {
            name = inputGrocery.text.toString()
            qty = inputQty.text.toString().toInt()
        }

        // if not, use default input
        else {
            if (inputGrocery.text.toString().isBlank() && inputQty.text.toString().isNotBlank()) {
                name = type
                qty = inputQty.text.toString().toInt()
            } else if (inputGrocery.text.toString().isNotBlank() && inputQty.text.toString().isBlank()) {
                name = inputGrocery.text.toString()
                qty = 1
            } else {
                name = type
                qty = 1
            }
        }

        // call DAO to insert grocery
        viewModel.addNewGrocery(
            (when (type) {
                "Raw" -> 1
                "Clean" -> 2
                "Water" -> 3
                "Snack" -> 4
                "Fruit or Veggies" -> 5
                else -> 6
            }),
            name,
            qty
        )

        val action = GroceryAddFragmentDirections.actionGroceryAddFragmentToGroceryList()
        this.findNavController().navigate(action)
    }
}