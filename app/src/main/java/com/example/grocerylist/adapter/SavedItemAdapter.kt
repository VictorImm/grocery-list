package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.data.Item
import com.example.grocerylist.dataViewModel.SavedItemViewModel

class SavedItemAdapter(
    private val itemList: LiveData<List<Item>>,
    private val savedItemViewModel: SavedItemViewModel,
) : RecyclerView.Adapter<SavedItemAdapter.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = itemView.findViewById(R.id.saved_item_name)
        val btnDelete : ImageButton = itemView.findViewById(R.id.btn_delete_saved)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.saved_item_row, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SavedItemAdapter.ListViewHolder, position: Int) {
        val item = itemList.value?.get(position)

        if (item != null) {
            holder.itemName.text = item.itemName
            holder.btnDelete.setOnClickListener {
                savedItemViewModel.deleteItem(item.id)
            }
        }
    }

    override fun getItemCount(): Int = itemList.value?.size ?: 0

    init {
        itemList.observeForever {
            notifyDataSetChanged()
        }
    }
}