package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R

class TypeAdapter(private val listType: List<Int>): RecyclerView.Adapter<TypeAdapter.ListViewHolder>() {

    class ListViewHolder(typeView: View): RecyclerView.ViewHolder(typeView) {
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
        // TODO: will used later in conditional
        val item = listType[position]

        // binding rv to other rv
        //TODO: to be change with room db (later)
        val data = ArrayList<String>(
            listOf(
                "String 1",
                "String 2",
                "String 3"
            )
        )
        val groceryAdapter = GroceryAdapter(data)

        // TODO: add conditional
        holder.tvTypeImage.setImageResource(R.drawable.ic_clean)
        holder.tvTypeText.text = "testing"
        holder.rvGroceryList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groceryAdapter
        }
    }

    override fun getItemCount(): Int = listType.size
}