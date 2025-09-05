package com.soczuks.footballassistant.ui.matches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.relations.MatchWithCompetitionAndItems

class MatchesViewModel(application: Application) : AndroidViewModel(application) {
    private val matchesDao = (application as FootballAssistantApp).getMatchesDao()

    val matches: LiveData<List<MatchWithCompetitionAndItems>> = matchesDao.getAllMatchesLive()
}