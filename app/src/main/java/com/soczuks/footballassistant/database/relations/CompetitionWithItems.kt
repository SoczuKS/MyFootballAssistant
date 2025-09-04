package com.soczuks.footballassistant.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.Item

data class CompetitionWithItems(
    @Embedded val competition: Competition,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val items: List<Item>
)