package com.example.grocerylist

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.GroceryViewModelFactory
import com.example.grocerylist.dataViewModel.SavedItemViewModel
import com.example.grocerylist.dataViewModel.SavedItemViewModelFactory
import com.example.grocerylist.databinding.FragmentGroceryAddBinding

class GroceryAddFragment : Fragment() {

    companion object {
        const val TYPE = "type"
    }

    var name: String = ""
    var qty: Int = 0

    private lateinit var typeId: String

    // binding
    private lateinit var binding: FragmentGroceryAddBinding

    // widgets
    private lateinit var inputGroceryLayout: com.google.android.material.textfield.TextInputLayout
    private lateinit var inputGrocery: com.google.android.material.textfield.TextInputEditText
    private lateinit var inputQty: com.google.android.material.textfield.TextInputEditText
    private lateinit var btnAdd: Button
    private lateinit var radioNew: RadioButton
    private lateinit var radioSaved: RadioButton
    private lateinit var dropdownTextView: AutoCompleteTextView
    private lateinit var dropdownMenu: com.google.android.material.textfield.TextInputLayout

    // viewModel initialization
    private val groceryViewModel: GroceryViewModel by viewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryApplication).database.groceryDao()
        )
    }

    private val savedItemViewModel: SavedItemViewModel by viewModels {
        SavedItemViewModelFactory(
            (activity?.application as GroceryApplication).database.itemDao()
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
        inputGroceryLayout = binding.layoutInputGrocery
        inputQty = binding.inputQty
        btnAdd = binding.btnAdd
        radioNew = binding.radioNew
        radioSaved = binding.radioSaved
        dropdownTextView = binding.dropdownTextView
        dropdownMenu = binding.dropdownMenu

        btnAdd.setOnClickListener { btnClicked(typeId) }

        radioNew.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                dropdownMenu.visibility = View.GONE
                inputGroceryLayout.visibility = View.VISIBLE
            }
        }

        radioSaved.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                inputGroceryLayout.visibility = View.GONE
                dropdownMenu.visibility = View.VISIBLE
            }
        }
        inputGrocery.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                name = p0.toString()
            }

        })
        savedItemViewModel.retrieveItems(
            when (typeId) {
                "Raw" -> 1
                "Clean" -> 2
                "Water" -> 3
                "Snack" -> 4
                "Fruit or Veggies" -> 5
                else -> 6
            }
        ).observe(this.viewLifecycleOwner) {
            if (it.isEmpty()) {
                radioSaved.isEnabled = false
            } else {
                var itemNames: List<String> = it.map { it -> it.itemName }
                val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, itemNames)
                dropdownTextView.setAdapter(arrayAdapter)
                dropdownTextView.setOnItemClickListener { adapterView, view, i, l ->
                    name = it[i].itemName
                }
            }
        }
    }

    private fun btnClicked(type: String) {
        Log.d("GroceryAddFragment", "name = $name")
        // check if entry is filled
        if (!groceryViewModel.isEntryValid(
                0,
                name,
                inputQty.text.toString()
            )
        ) {
            if (name.isBlank() && inputQty.text.toString().isNotBlank()) {
                name = type
                qty = inputQty.text.toString().toInt()
            } else if (name.isNotBlank() && inputQty.text.toString().isBlank()) {
                qty = 1
            } else {
                name = type
                qty = 1
            }
        }

        // call DAO to insert grocery
        groceryViewModel.addNewGrocery(
            (when (type) {
                "Raw" -> 1
                "Clean" -> 2
                "Water" -> 3
                "Snack" -> 4
                "Fruit or Veggies" -> 5
                else -> 6
            }),
            name,
            inputQty.text.toString().toInt()
        )

        val action = GroceryAddFragmentDirections.actionGroceryAddFragmentToGroceryList()
        this.findNavController().navigate(action)
    }
}