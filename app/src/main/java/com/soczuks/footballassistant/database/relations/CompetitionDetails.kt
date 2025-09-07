package com.soczuks.footballassistant.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.entities.Item

data class CompetitionDetails(
    @Embedded val competition: Competition,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = CompetitionItem::class,
            parentColumn = "competitionId",
            entityColumn = "itemId"
        )
    ) val items: List<Item>
)
