package com.example.grocerylist.dataViewModel

import androidx.lifecycle.*
import com.example.grocerylist.data.Grocery
import com.example.grocerylist.data.GroceryDao
import kotlinx.coroutines.launch

class GroceryViewModel(private val groceryDao: GroceryDao): ViewModel() {
    /* Cache all items from the database using LiveData
    * 1 = Raw
    * 2 = Clean
    * 3 = Water
    * 4 = Snack
    * 5 = Fruit/Veggie
    * 6 = Other
    */

    // Where status = not bough
    val nBoughtRaw: LiveData<List<Grocery>> = groceryDao.getItems(1, true).asLiveData()
    val nBoughtClean: LiveData<List<Grocery>> = groceryDao.getItems(2, true).asLiveData()
    val nBoughtWater: LiveData<List<Grocery>> = groceryDao.getItems(3, true).asLiveData()
    val nBoughtSnack: LiveData<List<Grocery>> = groceryDao.getItems(4, true).asLiveData()
    val nBoughtFruit: LiveData<List<Grocery>> = groceryDao.getItems(5, true).asLiveData()
    val nBoughtOther: LiveData<List<Grocery>> = groceryDao.getItems(6, true).asLiveData()

    // Where status = bought
    val boughtRaw: LiveData<List<Grocery>> = groceryDao.getItems(1, false).asLiveData()
    val boughtClean: LiveData<List<Grocery>> = groceryDao.getItems(2, false).asLiveData()
    val boughtWater: LiveData<List<Grocery>> = groceryDao.getItems(3, false).asLiveData()
    val boughtSnack: LiveData<List<Grocery>> = groceryDao.getItems(4, false).asLiveData()
    val boughtFruit: LiveData<List<Grocery>> = groceryDao.getItems(5, false).asLiveData()
    val boughtOther: LiveData<List<Grocery>> = groceryDao.getItems(6, false).asLiveData()

    // Insert item
    fun addNewGrocery(type: Int, name: String, qty: Int) {
        val newGrocery = Grocery(
            groType = type,
            groName = name,
            groQty = qty,
            groStat = true
        )
        viewModelScope.launch {
            groceryDao.insert(newGrocery)
        }
    }

    // Check if input is valid
    fun isEntryValid(type: Int, name: String, qty: String): Boolean {
        if (type.toString().isBlank() ||
            name.isBlank() ||
            qty.isBlank()) {
            return false
        }
        return true
    }

    // Retrieve item
    fun retrieveGrocery(id: Int): LiveData<Grocery> {
        return groceryDao.getItem(id).asLiveData()
    }

    // Retrieve type
    fun retrieveType(): LiveData<List<Int>> {
        return groceryDao.getType().asLiveData()
    }

    // Update item
    fun updateItem(id: Int, stat: Boolean) {
        viewModelScope.launch {
            groceryDao.updateItemStats(id, stat)
        }
    }

    // Truncate database
    fun truncateGrocery() {
        viewModelScope.launch {
            groceryDao.deleteAllGroceries()
        }
    }

}

class GroceryViewModelFactory(private val groceryDao: GroceryDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GroceryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GroceryViewModel(groceryDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}