package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Item

@Dao
interface ItemDao {
    @Insert
    suspend fun insert(item: Item)

    @Delete
    suspend fun delete(item: Item)

    @Query("SELECT * FROM items ORDER BY name ASC")
    @Transaction
    fun getAll(): LiveData<List<Item>>
}
