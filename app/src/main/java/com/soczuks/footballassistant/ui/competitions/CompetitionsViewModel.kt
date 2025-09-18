package com.soczuks.footballassistant.ui.competitions

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.relations.CompetitionDetails
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.launch

class CompetitionsViewModel(application: Application) : AndroidViewModel(application) {
    private val footballAssistantApp = application as FootballAssistantApp
    private val competitionsDao = footballAssistantApp.getCompetitionsDao()

    val competitions: LiveData<List<CompetitionDetails>> = competitionsDao.getAll()

    suspend fun insert(competition: Competition): Long? {
        var newCompetitionId: Long? = null
        try {
            newCompetitionId = competitionsDao.insert(competition)
        } catch (e: SQLiteConstraintException) {
            footballAssistantApp.setMessage(
                ViewModelMessage(ViewModelMessage.Code.InsertFailed, e.localizedMessage)
            )
        }
        return newCompetitionId
    }

    fun getCompetitionById(id: Long): LiveData<CompetitionDetails>? {
        return competitionsDao.getById(id)
    }

    fun delete(competition: CompetitionDetails) {
        viewModelScope.launch {
            try {
                competitionsDao.delete(competition.competition)
            } catch (e: SQLiteConstraintException) {
                footballAssistantApp.setMessage(
                    ViewModelMessage(ViewModelMessage.Code.DeleteFailed, e.localizedMessage)
                )
            }
        }.start()
    }

    fun assignItemToCompetition(competition: CompetitionDetails, item: Item) {
        viewModelScope.launch {
            competitionsDao.insert(
                CompetitionItem(
                    competition.competition.id, item.id
                )
            )
        }
    }

    fun unassignItemFromCompetition(competition: CompetitionDetails, item: Item) {
        viewModelScope.launch {
            competitionsDao.delete(
                CompetitionItem(
                    competition.competition.id, item.id
                )
            )
        }
    }
}
