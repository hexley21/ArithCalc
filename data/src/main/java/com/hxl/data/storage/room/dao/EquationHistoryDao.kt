package com.hxl.data.storage.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hxl.data.model.EquationDto

/**
 * Interface for database access for Game-History related operations.
 */
@Dao
interface EquationHistoryDao {

    @Query("SELECT * FROM equation_history ORDER BY date ASC")
    suspend fun readEquationHistory(): List<EquationDto>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEquationHistory(gameResultDto: EquationDto)

}