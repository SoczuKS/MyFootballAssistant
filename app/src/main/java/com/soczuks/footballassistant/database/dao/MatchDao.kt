package com.soczuks.footballassistant.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.relations.MatchWithCompetitionAndItems

@Dao
interface MatchDao {
    @Insert
    suspend fun insert(match: Match)

    @Query("SELECT * FROM matches")
    suspend fun getAllMatches(): List<MatchWithCompetitionAndItems>

    @Query("SELECT * FROM matches ORDER BY matchDate DESC")
    fun getAllMatchesLive(): LiveData<List<MatchWithCompetitionAndItems>>

    @Query("SELECT * FROM matches WHERE id = :matchId")
    suspend fun getMatchById(matchId: Int): MatchWithCompetitionAndItems?

    @Query("DELETE FROM matches WHERE id = :matchId")
    suspend fun deleteMatchById(matchId: Int)
}