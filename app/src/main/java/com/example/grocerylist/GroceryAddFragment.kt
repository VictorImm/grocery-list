package com.example.grocerylist

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
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
        btnAdd.setOnClickListener { btnClicked() }
    }

    private fun btnClicked() {
        //TODO: Access DAO

        val action = GroceryAddFragmentDirections.actionGroceryAddFragmentToGroceryList()
        this.findNavController().navigate(action)
    }
}