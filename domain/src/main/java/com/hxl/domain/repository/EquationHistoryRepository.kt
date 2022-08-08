package com.hxl.domain.repository

import com.hxl.domain.models.Equation

/**
 * Game History Repository interface for handling game results.
 */
interface EquationHistoryRepository {

    suspend fun readEquationHistory(): List<Equation>

    suspend fun insertEquationHistory(gameHistory: Equation)

}