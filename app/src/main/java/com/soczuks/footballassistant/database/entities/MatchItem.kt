package com.soczuks.footballassistant.database.entities

import androidx.room.Entity

@Entity(primaryKeys = ["matchId", "itemId"], tableName = "match_items")
data class MatchItem(
    val matchId: Int,
    val itemId: Int,
    val checked: Boolean
)