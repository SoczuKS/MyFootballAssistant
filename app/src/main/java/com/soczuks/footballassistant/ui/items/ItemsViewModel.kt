package com.soczuks.footballassistant.ui.items

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemsDao = (application as FootballAssistantApp).getItemsDao()

    val items: LiveData<List<Item>> = itemsDao.getAllItemsLive()

    fun insertItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemsDao.insert(item)
        }
    }
}