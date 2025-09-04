package com.soczuks.footballassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

@Dao
interface CompetitionDao {
    @Insert
    suspend fun insert(competition: Competition)

    @Transaction
    @Query("SELECT * FROM competitions")
    suspend fun getCompetitions(): List<CompetitionWithItems>

    @Transaction
    @Query("SELECT * FROM competitions WHERE id = :competitionId")
    suspend fun getCompetitionById(competitionId: Int): CompetitionWithItems?
}