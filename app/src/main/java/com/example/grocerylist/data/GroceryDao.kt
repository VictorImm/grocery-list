package com.example.grocerylist.data

import androidx.core.view.WindowInsetsCompat.Type.InsetsType
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GroceryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gro: Grocery)

    @Query("SELECT * from grocery WHERE type = :type AND stats = :stats ORDER BY name ASC")
    fun getItems(type: Int, stats: Boolean): Flow<List<Grocery>>

    @Query("SELECT * from grocery WHERE id = :id")
    fun getItem(id: Int): Flow<Grocery>

    @Query("SELECT DISTINCT type from grocery ORDER BY type ASC")
    fun getType(): Flow<List<Int>>

    @Query("UPDATE grocery SET stats = :stats WHERE id = :id")
    suspend fun updateItemStats(id: Int, stats: Boolean)

    @Query("DELETE FROM grocery")
    suspend fun deleteAllGroceries()
}