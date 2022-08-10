package com.hxl.data.storage.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.hxl.data.model.EquationDto
import com.hxl.data.storage.room.dao.EquationHistoryDao

/**
 * Main database description.
 */
@Database(
    entities = [EquationDto::class],
    version = 1,
    exportSchema = false
)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun equationHistoryDao(): EquationHistoryDao

    companion object {
        const val TAG = "LOCAL_DATABASE"
    }
}