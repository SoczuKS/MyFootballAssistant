package com.soczuks.footballassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soczuks.footballassistant.database.converters.DateTimeConverter
import com.soczuks.footballassistant.database.dao.CompetitionDao
import com.soczuks.footballassistant.database.dao.ItemDao
import com.soczuks.footballassistant.database.dao.MatchDao
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.CompetitionItem
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.entities.Match
import com.soczuks.footballassistant.database.entities.MatchItem

@Database(
    entities = [
        Match::class,
        Item::class,
        Competition::class,
        MatchItem::class,
        CompetitionItem::class
    ],
    exportSchema = false,
    version = 14
)
@TypeConverters(DateTimeConverter::class)
abstract class Database : RoomDatabase() {
    abstract fun matchDao(): MatchDao
    abstract fun itemDao(): ItemDao
    abstract fun competitionDao(): CompetitionDao
}
