package com.soczuks.footballassistant.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "match_items",
    primaryKeys = ["matchId", "itemId"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Match::class,
            parentColumns = ["id"],
            childColumns = ["matchId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
        androidx.room.ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["matchId"]), Index(value = ["itemId"])]
)
data class MatchItem(
    val matchId: Long,
    val itemId: Long,
    val checked: Boolean
)
