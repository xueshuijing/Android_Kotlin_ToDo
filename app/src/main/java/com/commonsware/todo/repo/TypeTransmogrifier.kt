package com.commonsware.todo.repo

import androidx.room.TypeConverter
import java.time.Instant

object TypeTransmogrifier {
    @TypeConverter
    fun fromInstant(date: Instant?): Long? = date?.toEpochMilli()

    @TypeConverter
    fun toInstant(milisSinceEpoch: Long?): Instant? = milisSinceEpoch?.let {
        Instant.ofEpochMilli(it)
    }

}