package com.soczuks.footballassistant.database.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

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
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val competitionId: Long,
    val rivalTeam: String,
    val isHome: Boolean,
    val matchDate: Date,
    val homeScore: Int? = null,
    val awayScore: Int? = null,
    val grade: Int? = null,
    val note: String? = null
)
