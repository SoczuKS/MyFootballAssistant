package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.relations.CompetitionDetails

@Dao
interface CompetitionDao {
    @Insert
    suspend fun insert(competition: Competition)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(competitionItem: CompetitionItem)

    @Delete
    suspend fun delete(competition: Competition)

    @Delete
    suspend fun delete(competitionItem: CompetitionItem)

    @Query("SELECT * FROM competitions ORDER BY name ASC")
    @Transaction
    fun getAll(): LiveData<List<CompetitionDetails>>

    @Query("SELECT * FROM competitions WHERE id = :competitionId")
    @Transaction
    fun getById(competitionId: Int): LiveData<CompetitionDetails>?
}
