package com.example.grocerylist.data

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gro: Grocery)

    @Update
    suspend fun update(gro: Grocery)

    @Delete
    suspend fun delete(gro: Grocery)

    @Query("SELECT * from grocery WHERE type = :type ORDER BY type ASC")
    fun getItems(type: Int): Flow<List<Grocery>>

    @Query("SELECT * from grocery WHERE id = :id")
    fun getItem(id: Int): Flow<Grocery>

    @Query("UPDATE grocery SET stats = :stats WHERE id = :id")
    fun updateItemStats(id: Int, stats: Boolean)

    @Query("DELETE FROM grocery")
    fun deleteAllGroceries()
}