package com.hxl.data.repository

import com.hxl.data.model.EquationDto
import com.hxl.data.storage.room.dao.EquationHistoryDao
import com.hxl.domain.models.Equation
import com.hxl.domain.repository.EquationHistoryRepository
import java.util.*

/**
 * Repository implementation that handles Game-History objects.
 */
class EquationHistoryRepositoryImpl(private val equationHistoryDao: EquationHistoryDao) :
    EquationHistoryRepository {

    override suspend fun readEquationHistory(): List<Equation> {
        return equationHistoryDao.readEquationHistory().map { EquationDto.toEquation(it) }
    }

    override suspend fun insertEquationHistory(equation: Equation) {
        return equationHistoryDao.insertEquationHistory(
            EquationDto(
                Date().time,
                equation.equation,
                equation.evaluation
            )
        )
    }

}