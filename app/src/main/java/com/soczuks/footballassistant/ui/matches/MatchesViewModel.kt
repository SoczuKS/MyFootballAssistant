package com.soczuks.footballassistant.ui.matches

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
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
