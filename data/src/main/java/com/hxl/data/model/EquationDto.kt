package com.hxl.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.hxl.domain.models.Equation

/**
 * Difficulty Value Object with id as a primary-key.
 * id key will always be 1, to provide easy replace on new record insert.
 */
@Entity(tableName = "equation_history")
data class EquationDto(
    @PrimaryKey
    @ColumnInfo(name = "date") val date: Long,
    @ColumnInfo(name = "equation") val equation: String,
    @ColumnInfo(name = "evaluation") val evaluation: String
){
    companion object {
        fun toEquation(equation: EquationDto): Equation {
            return Equation(
                equation.equation,
                equation.evaluation
            )
        }
    }
}