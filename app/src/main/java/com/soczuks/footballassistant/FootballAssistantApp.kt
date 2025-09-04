package com.soczuks.footballassistant

import android.app.Application
import androidx.room.Room
import com.soczuks.footballassistant.database.Database

class FootballAssistantApp: Application() {
    lateinit var database: Database
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            Database::class.java, "fa_db.db"
        ).build()
    }
}