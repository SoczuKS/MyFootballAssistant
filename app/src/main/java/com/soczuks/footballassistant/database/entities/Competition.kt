package com.soczuks.footballassistant.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "competitions")
data class Competition (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)