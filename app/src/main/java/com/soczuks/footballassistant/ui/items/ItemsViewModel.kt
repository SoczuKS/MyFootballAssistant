package com.soczuks.footballassistant.ui.items

import android.app.Application
import android.database.sqlite.SQLiteConstraintException
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.soczuks.footballassistant.FootballAssistantApp
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.ui.ViewModelMessage
import kotlinx.coroutines.launch

class ItemsViewModel(application: Application) : AndroidViewModel(application) {
    private val footballAssistantApp = application as FootballAssistantApp
    private val itemsDao = footballAssistantApp.getItemsDao()

    val items: LiveData<List<Item>> = itemsDao.getAll()

    suspend fun insert(item: Item): Long? {
        var newItemId: Long? = null
        try {
            newItemId = itemsDao.insert(item)
        } catch (e: SQLiteConstraintException) {
            footballAssistantApp.setMessage(
                ViewModelMessage(
                    ViewModelMessage.Code.InsertFailed, e.localizedMessage
                )
            )
        }
        return newItemId
    }

    fun delete(item: Item) {
        viewModelScope.launch {
            try {
                itemsDao.delete(item)
            } catch (e: SQLiteConstraintException) {
                footballAssistantApp.setMessage(
                    ViewModelMessage(
                        ViewModelMessage.Code.DeleteFailed, e.localizedMessage
                    )
                )
            }
        }.start()
    }
}
