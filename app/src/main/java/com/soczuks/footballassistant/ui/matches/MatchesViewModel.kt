package com.soczuks.footballassistant.ui.matches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.relations.MatchDetails
import kotlinx.coroutines.launch

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private val matchesDao = (application as FootballAssistantApp).getMatchesDao()

    val matches: LiveData<List<MatchDetails>> = matchesDao.getAll()

    fun getMatchById(id: Int): LiveData<MatchDetails>? {
        return matchesDao.getById(id)
    }

    fun delete(match: MatchDetails) {
        viewModelScope.launch {
            matchesDao.delete(match.match)
        }
    }
}
