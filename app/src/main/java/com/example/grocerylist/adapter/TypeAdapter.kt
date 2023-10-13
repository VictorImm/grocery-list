package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.SavedItemViewModel

class TypeAdapter(
    private val listType: List<Int>,
    private val groceryViewModel: GroceryViewModel,
    private val savedItemViewModel: SavedItemViewModel,
    private val activity: Int
) : RecyclerView.Adapter<TypeAdapter.ListViewHolder>() {

    class ListViewHolder(typeView: View) : RecyclerView.ViewHolder(typeView) {
        val tvTypeImage: ImageView = itemView.findViewById(R.id.type_image)
        val tvTypeText: TextView = itemView.findViewById(R.id.type_name)
        val rvGroceryList: RecyclerView = itemView.findViewById<RecyclerView?>(R.id.type_rv)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.type_row,
                    parent,
                    false
                )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TypeAdapter.ListViewHolder, position: Int) {
        val item = listType[position]

        // binding rv to other rv
        when (activity) {
            1 -> {
                val groceryAdaptersList: MutableList<GroceryAdapter> = mutableListOf()

                // Initialising groceryAdapter
                val groceryAdapter = GroceryAdapter(
                    when (item) {
                        1 -> groceryViewModel.nBoughtRaw
                        2 -> groceryViewModel.nBoughtClean
                        3 -> groceryViewModel.nBoughtWater
                        4 -> groceryViewModel.nBoughtSnack
                        5 -> groceryViewModel.nBoughtFruit
                        else -> groceryViewModel.nBoughtOther
                    },
                    groceryViewModel,
                    savedItemViewModel
                )

                // Save groceryAdapter same as position
                if (groceryAdaptersList.size <= position) {
                    groceryAdaptersList.add(groceryAdapter)
                } else {
                    groceryAdaptersList[position] = groceryAdapter
                }

                holder.rvGroceryList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = groceryAdapter
                }
            }

            2 -> {
                val cartAdaptersList: MutableList<CartAdapter> = mutableListOf()

                // Initialising cartAdapter
                val cartAdapter = CartAdapter(
                    when (item) {
                        1 -> groceryViewModel.boughtRaw
                        2 -> groceryViewModel.boughtClean
                        3 -> groceryViewModel.boughtWater
                        4 -> groceryViewModel.boughtSnack
                        5 -> groceryViewModel.boughtFruit
                        else -> groceryViewModel.boughtOther
                    },
                    groceryViewModel
                )

                // Save groceryAdapter same as position
                if (cartAdaptersList.size <= position) {
                    cartAdaptersList.add(cartAdapter)
                } else {
                    cartAdaptersList[position] = cartAdapter
                }

                holder.rvGroceryList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = cartAdapter
                }
            }

            else -> {
                var savedItemAdapterList: MutableList<SavedItemAdapter> = mutableListOf()

                // Initialising savedItemAdapter
                val savedItemAdapter: SavedItemAdapter = SavedItemAdapter(
                    when (item) {
                        1 -> savedItemViewModel.nRaw
                        2 -> savedItemViewModel.nClean
                        3 -> savedItemViewModel.nWater
                        4 -> savedItemViewModel.nSnack
                        5 -> savedItemViewModel.nFruit
                        else -> savedItemViewModel.nOther
                    },
                    savedItemViewModel
                )

                holder.rvGroceryList.apply {
                    layoutManager = LinearLayoutManager(context)
                    adapter = savedItemAdapter
                }
            }
        }

        holder.tvTypeImage.setImageResource(
            when (item) {
                1 -> R.drawable.ic_raw
                2 -> R.drawable.ic_clean
                3 -> R.drawable.ic_drink
                4 -> R.drawable.ic_snack
                5 -> R.drawable.ic_fruit
                else -> R.drawable.ic_other
            }
        )
        holder.tvTypeText.text =
            when (item) {
                1 -> "Raw"
                2 -> "Clean"
                3 -> "Water"
                4 -> "Snack"
                5 -> "Fruit/Veggie"
                else -> "Other"
            }
    }

    override fun getItemCount(): Int = listType.size
}