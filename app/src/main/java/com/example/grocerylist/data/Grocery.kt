package com.example.grocerylist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grocery")
data class Grocery (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "type")
    val groType: Int,

    @ColumnInfo(name = "name")
    val groName: String,

    @ColumnInfo(name = "qty")
    val groQty: Int,

    @ColumnInfo(name = "stats")
    val groStat: Boolean
)