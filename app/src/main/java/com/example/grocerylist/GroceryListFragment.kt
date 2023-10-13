package com.example.grocerylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.TypeAdapter
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.GroceryViewModelFactory
import com.example.grocerylist.dataViewModel.SavedItemViewModel
import com.example.grocerylist.dataViewModel.SavedItemViewModelFactory
import com.example.grocerylist.databinding.FragmentGroceryListBinding

class GroceryListFragment : Fragment() {

    // binding
    private lateinit var binding: FragmentGroceryListBinding

    // widgets
    private lateinit var rvItems: RecyclerView
    private lateinit var floatingAdd: com.google.android.material.floatingactionbutton.FloatingActionButton

    // viewModel initialization
    private val viewModel: GroceryViewModel by viewModels {
        GroceryViewModelFactory(
            (activity?.application as GroceryApplication).database.groceryDao()
        )
    }

    private val savedItemViewModel : SavedItemViewModel by viewModels {
        SavedItemViewModelFactory(
            (activity?.application as GroceryApplication).database.itemDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGroceryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        floatingAdd = binding.floatingAddBtn
        floatingAdd.setOnClickListener {
            val action = GroceryListFragmentDirections.actionGroceryListToTypeChooseFragment()
            this.findNavController().navigate(action)
        }

        rvItems = binding.rvItems
        rvItems.setHasFixedSize(true)

        viewModel.retrieveType().observe(this.viewLifecycleOwner) {
            showRecyclerList(it)
        }
    }

    private fun showRecyclerList(items: List<Int>) {
        rvItems.layoutManager = LinearLayoutManager(this.context)

        // show recycle view from list of type
        val itemAdapter = TypeAdapter(items, viewModel, savedItemViewModel, 1)
        rvItems.adapter = itemAdapter
    }

}