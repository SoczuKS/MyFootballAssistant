package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

@Dao
interface CompetitionDao {
    @Insert
    suspend fun insert(competition: Competition)

    @Query("SELECT * FROM competitions")
    suspend fun getAllCompetitions(): List<CompetitionWithItems>

    @Query("SELECT * FROM competitions ORDER BY name ASC")
    fun getAllCompetitionsLive(): LiveData<List<CompetitionWithItems>>

    @Query("SELECT * FROM competitions WHERE id = :competitionId")
    suspend fun getCompetitionById(competitionId: Int): CompetitionWithItems?

    @Query("DELETE FROM items WHERE id = :competitionId")
    suspend fun deleteItemById(competitionId: Int)
}