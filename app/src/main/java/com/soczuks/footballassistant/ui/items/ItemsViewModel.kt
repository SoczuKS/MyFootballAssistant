package com.soczuks.footballassistant.ui.items

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val itemsDao = (application as FootballAssistantApp).getItemsDao()
    private val _viewModelMessage = MutableStateFlow<ViewModelMessage?>(null)

    val items: LiveData<List<Item>> = itemsDao.getAll()
    val viewModelMessage: StateFlow<ViewModelMessage?> = _viewModelMessage

    fun delete(item: Item) {
        viewModelScope.launch {
            try {
                itemsDao.delete(item)
            } catch (e: SQLiteConstraintException) {
                _viewModelMessage.value = ViewModelMessage(
                    ViewModelMessage.Code.DeleteFailed,
                    e.localizedMessage
                )
            }
        }.start()
    }

    fun clearMessage() {
        _viewModelMessage.value = null
    }
}
