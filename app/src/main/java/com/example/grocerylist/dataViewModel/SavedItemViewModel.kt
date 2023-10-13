package com.example.grocerylist.dataViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.grocerylist.data.Grocery
import com.example.grocerylist.data.Item
import com.example.grocerylist.data.ItemDao
import kotlinx.coroutines.launch

class SavedItemViewModel(private val itemDao: ItemDao) : ViewModel() {

    val nRaw: LiveData<List<Item>> = itemDao.getItems(1).asLiveData()
    val nClean: LiveData<List<Item>> = itemDao.getItems(2).asLiveData()
    val nWater: LiveData<List<Item>> = itemDao.getItems(3).asLiveData()
    val nSnack: LiveData<List<Item>> = itemDao.getItems(4).asLiveData()
    val nFruit: LiveData<List<Item>> = itemDao.getItems(5).asLiveData()
    val nOther: LiveData<List<Item>> = itemDao.getItems(6).asLiveData()

    fun addNewItem(name: String, type: Int) {
        val item = Item(
            itemName = name,
            itemType = type
        )

        viewModelScope.launch {
            itemDao.insert(item)
        }
    }

    fun retrieveType() : LiveData<List<Int>> {
        return itemDao.getType().asLiveData()
    }

    fun retrieveItem(id: Int): LiveData<Item> {
        return itemDao.getItem(id).asLiveData()
    }

    fun retrieveItems(type: Int): LiveData<List<Item>> {
        return itemDao.getItems(type).asLiveData()
    }

    fun deleteItem(id:Int) {
        viewModelScope.launch {
            itemDao.deleteItem(id)
        }
    }
}

class SavedItemViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedItemViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavedItemViewModel(itemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}