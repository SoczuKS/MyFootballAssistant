package com.soczuks.footballassistant.database.converters

import androidx.room.TypeConverter

class DateTimeConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): java.util.Date? = value?.let { java.util.Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: java.util.Date?): Long? = date?.time
}
