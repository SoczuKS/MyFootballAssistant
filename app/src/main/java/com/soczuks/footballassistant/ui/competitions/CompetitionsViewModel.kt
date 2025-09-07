package com.soczuks.footballassistant.ui.competitions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.relations.CompetitionWithItems
import kotlinx.coroutines.launch

class CompetitionsViewModel(application: Application) : AndroidViewModel(application) {
    private val competitionsDao = (application as FootballAssistantApp).getCompetitionsDao()

    val competitions: LiveData<List<CompetitionWithItems>> =
        competitionsDao.getAllCompetitionsLive()

    fun getCompetitionById(id: Int): LiveData<CompetitionWithItems>? {
        return competitionsDao.getCompetitionById(id)
    }

    fun assignItemToCompetition(competition: CompetitionWithItems, item: Item) {
        viewModelScope.launch {
            competitionsDao.insertCompetitionItem(
                CompetitionItem(
                    competition.competition.id,
                    item.id
                )
            )
        }
    }

    fun unassignItemFromCompetition(competition: CompetitionWithItems, item: Item) {
        viewModelScope.launch {
            competitionsDao.deleteCompetitionItem(
                competition.competition.id,
                item.id

            )
        }
    }
}
