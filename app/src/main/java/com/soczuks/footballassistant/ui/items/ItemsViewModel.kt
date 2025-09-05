package com.soczuks.footballassistant.ui.items

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Item

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemsDao = (application as FootballAssistantApp).getItemsDao()

    val items: LiveData<List<Item>> = itemsDao.getAllItemsLive()
}
