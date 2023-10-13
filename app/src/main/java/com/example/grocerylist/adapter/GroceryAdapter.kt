package com.example.grocerylist.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.grocerylist.R
import com.example.grocerylist.data.Grocery
import com.example.grocerylist.dataViewModel.GroceryViewModel
import com.example.grocerylist.dataViewModel.SavedItemViewModel

class GroceryAdapter(
    private val listGrocery: LiveData<List<Grocery>>,
    private val groceryViewModel: GroceryViewModel,
    private val savedItemViewModel: SavedItemViewModel
): RecyclerView.Adapter<GroceryAdapter.ListViewHolder>() {

    class ListViewHolder(groceryView: View): RecyclerView.ViewHolder(groceryView) {
        val chkBox: CheckBox = itemView.findViewById(R.id.checkBox)
        val groQty: TextView = itemView.findViewById(R.id.text_qty)
        val menuBtn : TextView = itemView.findViewById(R.id.menu_btn)
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

            // Listen to checkbox changes:
            holder.chkBox.setOnCheckedChangeListener { checkBox, state ->
                // Responds to when the checkbox changes state.
                if (state) {
                    groceryViewModel.updateItem(item.id, false)
                }
            }

            holder.menuBtn.setOnClickListener { view ->
                val popupMenu : PopupMenu = PopupMenu(view.context, holder.menuBtn)
                popupMenu.inflate(R.menu.grocery_item_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId) {
                        R.id.save_item -> {
                            savedItemViewModel.addNewItem(item.groName, item.groType)
                            Toast.makeText(view.context, "${item.groName} Saved", Toast.LENGTH_SHORT).show()
                            true
                        }

                        R.id.delete_item -> {
                            groceryViewModel.deleteItem(item.id)
                            Toast.makeText(view.context, "${item.groName} deleted", Toast.LENGTH_SHORT).show()
                            true
                        }

                        else -> {
                            false
                        }
                    }
                }

                popupMenu.show()
            }
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