package com.example.grocerylist.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item:Item)

    @Query("SELECT * FROM items WHERE type = :type ORDER BY name ASC")
    fun getItems(type:Int) : Flow<List<Item>>

    @Query("SELECT DISTINCT type from items ORDER BY type ASC")
    fun getType() : Flow<List<Int>>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id:Int) : Flow<Item>

    @Query("DELETE FROM items WHERE id = :id")
    suspend fun deleteItem(id:Int)
}