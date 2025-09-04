package com.soczuks.footballassistant.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.relations.MatchWithCompetitionAndItems

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: Match)

    @Transaction
    @Query("SELECT * FROM matches")
    suspend fun getAllMatches(): List<MatchWithCompetitionAndItems>

    @Transaction
    @Query("SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): MatchWithCompetitionAndItems?

    @Query("DELETE FROM matches WHERE id = :matchId")
    suspend fun deleteMatchById(matchId: Int)
}