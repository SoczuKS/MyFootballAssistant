package com.soczuks.footballassistant.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.soczuks.footballassistant.database.converters.DateTimeConverter
import com.soczuks.footballassistant.database.entities.Competition
import com.soczuks.footballassistant.database.entities.Item
import com.soczuks.footballassistant.database.entities.Match

@Database(entities = [Match::class, Item::class, Competition::class], version = 4)
@TypeConverters(DateTimeConverter::class)
abstract class Database : RoomDatabase()
