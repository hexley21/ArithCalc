package com.hxl.arithcalc.presentation.fragment.calculator

import androidx.lifecycle.ViewModel
import com.hxl.domain.usecase.database.history.InsertEquationHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalculatorViewModel @Inject constructor(val insertEquationHistory: InsertEquationHistory) :
    ViewModel()