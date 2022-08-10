package com.hxl.arithcalc.presentation.fragment.calculator
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hxl.arithcalc.databinding.FragmentCalculatorBinding
import com.hxl.arithcalc.presentation.fragment.calculator.keyboard.KeyboardCalculator
import kotlin.math.ceil


class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initKeyboard()
        initBottomSheet()
    }

    private fun initKeyboard() {
        val editText: EditText = binding.inputField
        val keyboard: KeyboardCalculator = binding.softKeyboard

        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setTextIsSelectable(true)
        editText.showSoftInputOnFocus = false

        val ic : InputConnection = editText.onCreateInputConnection(EditorInfo())
        keyboard.setConnection(ic, binding.resultField)
    }

    private fun initBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            // peek with 35dp (100dp - margin(65dp))
            peekHeight = ceil(100 * resources.displayMetrics.density).toInt()
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        var isExpanded = false
        binding.bottomSheet.setOnClickListener{
            if (!isExpanded){
                isExpanded = true
                BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_EXPANDED
            }
            else{
                isExpanded = false
                BottomSheetBehavior.from(binding.bottomSheet).state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }
}