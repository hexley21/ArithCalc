package com.hxl.arithcalc.presentation.fragment.equation_history

import androidx.lifecycle.ViewModel
import com.hxl.domain.usecase.database.history.ReadEquationHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EquationHistoryViewModel @Inject constructor(val readEquationHistory: ReadEquationHistory) :
    ViewModel()