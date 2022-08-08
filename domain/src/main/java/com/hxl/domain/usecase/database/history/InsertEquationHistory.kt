package com.hxl.domain.usecase.database.history

import com.hxl.domain.models.Equation
import com.hxl.domain.repository.EquationHistoryRepository

/**
 * Game-History use-case that provides Game-Result insertion method.
 */
class InsertEquationHistory(private val equationHistoryRepository: EquationHistoryRepository) {
    suspend operator fun invoke(equation: Equation) {
        return equationHistoryRepository.insertEquationHistory(equation)
    }
}