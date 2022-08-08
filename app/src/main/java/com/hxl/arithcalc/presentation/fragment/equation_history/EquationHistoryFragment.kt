package com.hxl.arithcalc.presentation.fragment.equation_history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hxl.arithcalc.databinding.FragmentEquationHistoryBinding
import com.hxl.domain.models.Equation
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EquationHistoryFragment : Fragment() {

    private val vm: EquationHistoryViewModel by viewModels()
    private lateinit var binding: FragmentEquationHistoryBinding
    var history: List<Equation> = emptyList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEquationHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.topHistoryBar.setNavigationOnClickListener { parentFragmentManager.popBackStack() }
    }
}