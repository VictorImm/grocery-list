package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.data.Grocery

//TODO: dont forget to change List<String> to List<Grocery>
class GroceryAdapter(private val listGrocery: List<String>): RecyclerView.Adapter<GroceryAdapter.ListViewHolder>() {

    class ListViewHolder(groceryView: View): RecyclerView.ViewHolder(groceryView) {
        val chkBox: CheckBox = itemView.findViewById(R.id.checkBox)
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
        val item = listGrocery[position]
        holder.chkBox.text = item

        //TODO: add function if checkBox is clicked
    }

    override fun getItemCount(): Int = listGrocery.size
}