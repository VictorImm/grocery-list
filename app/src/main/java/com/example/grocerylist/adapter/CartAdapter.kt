package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.data.Grocery
import com.example.grocerylist.dataViewModel.GroceryViewModel

class CartAdapter(
    private val listGrocery: LiveData<List<Grocery>>,
    private val groceryViewModel: GroceryViewModel
): RecyclerView.Adapter<CartAdapter.ListViewHolder>() {

    class ListViewHolder(cartView: View): RecyclerView.ViewHolder(cartView) {
        val groName: TextView = itemView.findViewById(R.id.text_item)
        val groQty: TextView = itemView.findViewById(R.id.text_qty)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.cart_row,
                    parent,
                    false
                )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartAdapter.ListViewHolder, position: Int) {
        val item = listGrocery.value?.get(position)

        if (item != null) {
            holder.groName.text = item.groName
            holder.groQty.text = item.groQty.toString()
        }
    }

    override fun getItemCount(): Int = listGrocery.value?.size?: 0

    init {
        // Observer untuk LiveData listGrocery
        listGrocery.observeForever {
            // Di sini Anda dapat memperbarui data di dalam adapter RecyclerView
            notifyDataSetChanged()
        }
    }

}