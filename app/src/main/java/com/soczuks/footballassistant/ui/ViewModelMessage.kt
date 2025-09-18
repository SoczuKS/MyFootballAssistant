package com.soczuks.footballassistant.ui

class ViewModelMessage(val messageCode: Code, val message: String?) {
    enum class Code(val messageCode: Int) {
        Success(0),
        DeleteFailed(1),
        InsertFailed(2)
    }
}