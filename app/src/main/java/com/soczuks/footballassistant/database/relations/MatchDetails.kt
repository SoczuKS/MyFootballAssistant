package com.soczuks.footballassistant.database.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.entities.MatchItem

data class MatchDetails(
    @Embedded val match: Match,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = MatchItem::class,
            parentColumn = "matchId",
            entityColumn = "itemId"
        ),
        projection = ["id", "name"]
    ) val items: List<Item>,

    @Relation(
        parentColumn = "competitionId",
        entityColumn = "id"
    ) val competition: Competition
)
