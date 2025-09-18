package com.soczuks.footballassistant

import android.app.Application
import androidx.room.Room
import com.soczuks.footballassistant.database.Database
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FootballAssistantApp : Application() {
    private lateinit var database: Database
    private val _messageChannel = MutableStateFlow<ViewModelMessage?>(null)
    var messageChannel: StateFlow<ViewModelMessage?> = _messageChannel

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext, Database::class.java, "fa_db.db"
        ).fallbackToDestructiveMigration(true).build()
    }

    fun getItemsDao() = database.itemDao()
    fun getCompetitionsDao() = database.competitionDao()
    fun getMatchesDao() = database.matchDao()
    fun setMessage(message: ViewModelMessage) {
        _messageChannel.value = message
    }

    fun clearMessage() {
        _messageChannel.value = null
    }
}
