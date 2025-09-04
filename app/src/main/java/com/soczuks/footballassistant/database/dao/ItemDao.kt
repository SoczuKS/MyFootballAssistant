package com.soczuks.footballassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Item

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Query("SELECT * FROM items")
    suspend fun getAllItems(): List<Item>

    @Transaction
    @Query("SELECT * FROM items WHERE id = :itemId")
    suspend fun getItemById(itemId: Int): Item?

    @Query("DELETE FROM items WHERE id = :itemId")
    suspend fun deleteItemById(itemId: Int)
}