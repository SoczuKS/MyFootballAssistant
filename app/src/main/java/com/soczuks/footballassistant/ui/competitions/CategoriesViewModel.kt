package com.soczuks.footballassistant.ui.competitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CategoriesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is competition Fragment"
    }
    val text: LiveData<String> = _text
}