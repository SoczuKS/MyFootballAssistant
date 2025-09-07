package com.soczuks.footballassistant.database.entities

import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = "competition_items",
    primaryKeys = ["competitionId", "itemId"],
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = Competition::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = androidx.room.ForeignKey.CASCADE,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
        androidx.room.ForeignKey(
            entity = Item::class,
            parentColumns = ["id"],
            childColumns = ["itemId"],
            onDelete = androidx.room.ForeignKey.RESTRICT,
            onUpdate = androidx.room.ForeignKey.CASCADE
        ),
    ],
    indices = [Index(value = ["competitionId"]), Index(value = ["itemId"])]
)
class CompetitionItem(
    val competitionId: Int, val itemId: Int
)