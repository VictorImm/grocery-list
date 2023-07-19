package com.example.grocerylist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.adapter.TypeAdapter
import com.example.grocerylist.databinding.FragmentGroceryListBinding

class GroceryList : Fragment() {

    // binding
    private lateinit var binding: FragmentGroceryListBinding

    // widgets
    private lateinit var rvItems: RecyclerView

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

        rvItems = binding.rvItems
        rvItems.setHasFixedSize(true)

        showRecyclerList(ArrayList<Int>(
            listOf(
                1
            )
        ))
    }

    private fun showRecyclerList(items: List<Int>) {
        rvItems.layoutManager = LinearLayoutManager(this.context)
        // show recycle view from selected month
        val itemAdapter = TypeAdapter(items)
        rvItems.adapter = itemAdapter
    }

}