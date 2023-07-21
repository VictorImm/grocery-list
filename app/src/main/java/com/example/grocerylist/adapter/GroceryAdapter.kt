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

class GroceryAdapter(private val listGrocery: LiveData<List<Grocery>>): RecyclerView.Adapter<GroceryAdapter.ListViewHolder>() {

    class ListViewHolder(groceryView: View): RecyclerView.ViewHolder(groceryView) {
        val chkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val groQty: TextView = itemView.findViewById(R.id.text_qty)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.grocery_row,
                    parent,
                    false
                )
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroceryAdapter.ListViewHolder, position: Int) {
        val item = listGrocery.value?.get(position)

        if (item != null) {
            holder.chkBox.text = item.groName
            holder.groQty.text = item.groQty.toString()

            //TODO: add function if checkBox is clicked
        }
    }

    override fun getItemCount(): Int = listGrocery.value?.size?: 0

    init {
        // Observer untuk LiveData nBoughtRaw
        listGrocery.observeForever {
            // Di sini Anda dapat memperbarui data di dalam adapter RecyclerView
            notifyDataSetChanged()
        }
    }
}