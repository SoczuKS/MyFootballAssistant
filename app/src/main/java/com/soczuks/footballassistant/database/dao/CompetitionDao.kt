package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

@Dao
interface CompetitionDao {
    @Insert
    suspend fun insert(competition: Competition)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertCompetitionItem(competitionItem: CompetitionItem)

    @Query("DELETE FROM competition_items WHERE competitionId = :competitionId AND itemId = :itemId")
    suspend fun deleteCompetitionItem(competitionId: Int, itemId: Int)

    @Query("SELECT * FROM competitions")
    @Transaction
    suspend fun getAllCompetitions(): List<CompetitionWithItems>

    @Query("SELECT * FROM competitions ORDER BY name ASC")
    @Transaction
    fun getAllCompetitionsLive(): LiveData<List<CompetitionWithItems>>

    @Query("SELECT * FROM competitions WHERE id = :competitionId")
    @Transaction
    fun getCompetitionById(competitionId: Int): LiveData<CompetitionWithItems>?

    @Query("DELETE FROM items WHERE id = :competitionId")
    suspend fun deleteItemById(competitionId: Int)
}
