package com.soczuks.footballassistant.ui.competitions

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.relations.CompetitionWithItems

class CompetitionsViewModel(application: Application) : AndroidViewModel(application) {
    private val competitionsDao = (application as FootballAssistantApp).getCompetitionsDao()

    val competitions: LiveData<List<CompetitionWithItems>> =
        competitionsDao.getAllCompetitionsLive()
}