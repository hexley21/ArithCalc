package com.hxl.domain.usecase.database.history

import com.hxl.domain.models.Equation
import com.hxl.domain.repository.EquationHistoryRepository

/**
 * Game-History use-case that provides Game-Result records.
 */
class ReadEquationHistory(private val equationHistoryRepository: EquationHistoryRepository) {
    suspend operator fun invoke(): List<Equation> {
        return equationHistoryRepository.readEquationHistory()
    }
}