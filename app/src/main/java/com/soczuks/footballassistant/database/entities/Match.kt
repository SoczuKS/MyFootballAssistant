package com.soczuks.footballassistant.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "matches",
    foreignKeys = [
        ForeignKey(
            entity = Competition::class,
            parentColumns = ["id"],
            childColumns = ["competitionId"],
            onDelete = ForeignKey.RESTRICT
        )
    ],
    indices = [Index(value = ["competitionId"])]
)
data class Match(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val competitionId: Int,
    val homeTeam: String,
    val awayTeam: String,
    val matchDate: Long,
    val homeScore: Int?,
    val awayScore: Int?,
    val grade: Int?,
    val note: String?
)
