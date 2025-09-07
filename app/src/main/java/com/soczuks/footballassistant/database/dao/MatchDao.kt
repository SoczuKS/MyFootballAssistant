package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.entities.MatchItem
import com.soczuks.footballassistant.database.relations.MatchDetails

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: Match)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(matchItem: MatchItem)

    @Delete
    suspend fun delete(match: Match)

    @Delete
    suspend fun delete(matchItem: MatchItem)

    @Query("SELECT * FROM matches ORDER BY matchDate DESC")
    @Transaction
    fun getAll(): LiveData<List<MatchDetails>>

    @Query("SELECT * FROM matches WHERE id = :matchId")
    @Transaction
    fun getById(matchId: Int): LiveData<MatchDetails>?
}
