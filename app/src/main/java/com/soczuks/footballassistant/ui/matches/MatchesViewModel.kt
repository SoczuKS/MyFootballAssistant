package com.soczuks.footballassistant.ui.matches

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.entities.MatchItem
import com.soczuks.footballassistant.database.relations.MatchDetails
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private val matchesDao = (application as FootballAssistantApp).getMatchesDao()
    private val _viewModelMessage = MutableStateFlow<ViewModelMessage?>(null)

    val matches: LiveData<List<MatchDetails>> = matchesDao.getAll()
    val viewModelMessage: StateFlow<ViewModelMessage?> = _viewModelMessage

    suspend fun insert(match: Match): Long? {
        var newMatchId: Long? = null
        try {
            newMatchId = matchesDao.insert(match)
        } catch (e: SQLiteConstraintException) {
            _viewModelMessage.value = ViewModelMessage(
                ViewModelMessage.Code.InsertFailed,
                e.localizedMessage
            )
        }
        return newMatchId
    }

    fun insert(matchItem: MatchItem) {
        viewModelScope.launch {
            try {
                matchesDao.insert(matchItem)
            } catch (e: SQLiteConstraintException) {
                _viewModelMessage.value = ViewModelMessage(
                    ViewModelMessage.Code.InsertFailed,
                    e.localizedMessage
                )
            }
        }
    }

    fun getMatchById(id: Long): LiveData<MatchDetails>? {
        return matchesDao.getById(id)
    }

    fun delete(match: MatchDetails) {
        viewModelScope.launch {
            try {
                matchesDao.delete(match.match)
            } catch (e: SQLiteConstraintException) {
                _viewModelMessage.value = ViewModelMessage(
                    ViewModelMessage.Code.DeleteFailed,
                    e.localizedMessage
                )
            }
        }
    }

    fun clearMessage() {
        _viewModelMessage.value = null
    }
}
