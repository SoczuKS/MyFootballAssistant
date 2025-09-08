package com.soczuks.footballassistant

import android.app.Application
import androidx.room.Room
import com.soczuks.footballassistant.database.Database
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.entities.Match

class FootballAssistantApp : Application() {
    private lateinit var database: Database

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext, Database::class.java, "fa_db.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    fun getItemsDao() = database.itemDao()
    fun getCompetitionsDao() = database.competitionDao()
    fun getMatchesDao() = database.matchDao()

    suspend fun addItem(item: Item): Long {
        return database.itemDao().insert(item)
    }

    suspend fun addCompetition(competition: Competition): Long {
        return database.competitionDao().insert(competition)
    }

    suspend fun addMatch(match: Match): Long {
        return database.matchDao().insert(match)
    }
}
