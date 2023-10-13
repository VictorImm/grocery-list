package com.example.grocerylist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Grocery::class, Item::class],
    version = 2,
    exportSchema = false
)
abstract class GroceryRoomDatabase: RoomDatabase() {
    abstract fun groceryDao(): GroceryDao

    abstract fun itemDao() : ItemDao

    companion object {
        @Volatile
        private var INSTANCE: GroceryRoomDatabase? = null

        fun getDatabase(context: Context): GroceryRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroceryRoomDatabase::class.java,
                    "item_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}