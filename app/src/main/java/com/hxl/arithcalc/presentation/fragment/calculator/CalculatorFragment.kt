package com.hxl.arithcalc.presentation.fragment.calculator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.hxl.arithcalc.R
import com.hxl.arithcalc.databinding.FragmentCalculatorBinding
import com.hxl.arithcalc.presentation.activity.MainActivity
import com.hxl.arithcalc.presentation.fragment.calculator.keyboard.KeyboardCalculator
import com.hxl.arithcalc.presentation.fragment.dialogs.theme.ThemeDialog
import com.hxl.arithcalc.presentation.fragment.equation_history.EquationHistoryFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.ceil

@AndroidEntryPoint
class CalculatorFragment : Fragment() {
    private lateinit var binding: FragmentCalculatorBinding
    private val vm: CalculatorViewModel by viewModels()
    private val themeDialog = ThemeDialog()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initKeyboard()
        initBottomSheet()
        initNavigation()
    }

    private fun initKeyboard() {
        val editText: EditText = binding.inputField
        val keyboard: KeyboardCalculator = binding.softKeyboard

        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setTextIsSelectable(true)
        editText.showSoftInputOnFocus = false

        val ic: InputConnection = editText.onCreateInputConnection(EditorInfo())
        keyboard.setConnection(ic, binding.resultField, vm.insertEquationHistory)
    }

    private fun initBottomSheet() {
        BottomSheetBehavior.from(binding.bottomSheet).apply {
            // peek with 35dp (100dp - margin(65dp))
            peekHeight = ceil(100 * resources.displayMetrics.density).toInt()
            state = BottomSheetBehavior.STATE_COLLAPSED
        }
        var isExpanded = false
        binding.bottomSheet.setOnClickListener {
            if (!isExpanded) {
                isExpanded = true
                BottomSheetBehavior.from(binding.bottomSheet).state =
                    BottomSheetBehavior.STATE_EXPANDED
            } else {
                isExpanded = false
                BottomSheetBehavior.from(binding.bottomSheet).state =
                    BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun initNavigation() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.theme -> {
                    (requireActivity() as MainActivity).showDialog(themeDialog, ThemeDialog.TAG)
                    true
                }
                R.id.rate -> {
                    try {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("market://details?id=${requireActivity().packageName}")
                            )
                        )
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=${requireActivity().packageName}")
                            )
                        )
                    }
                    true
                }
                R.id.licenses -> {
                    startActivity(Intent(requireContext(), OssLicensesMenuActivity::class.java))
                    true
                }
                else -> false
            }
        }

        binding.topAppBar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).replaceFragment(EquationHistoryFragment(), true)
        }
    }

    override fun onStop() {
        super.onStop()
        if (themeDialog.isStateSaved || themeDialog.isAdded || themeDialog.isVisible) {
            themeDialog.dismissAllowingStateLoss()
        }
    }
}