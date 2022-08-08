package com.hxl.arithcalc.presentation.fragment.calculator

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputConnection
import android.widget.Button
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
class CalculatorFragment : Fragment(), View.OnClickListener {
    private lateinit var binding: FragmentCalculatorBinding
    private val vm: CalculatorViewModel by viewModels()
    private val keyValues = SparseArray<String>()
    private val themeDialog = ThemeDialog()
    private lateinit var ic: InputConnection

    private lateinit var btnPi: Button
    private lateinit var btnBracketOpen: Button
    private lateinit var btnBracketClose: Button
    private lateinit var btnRoot: Button
    private lateinit var btnSin: Button
    private lateinit var btnCos: Button
    private lateinit var btnTan: Button
    private lateinit var btnCot: Button
    private lateinit var btnLog: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentCalculatorBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initKeyboard()
        initBottomSheet()
        initSheetKeyboard()
        initNavigation()
    }

    private fun initKeyboard() {
        val editText: EditText = binding.inputField
        val keyboard: KeyboardCalculator = binding.softKeyboard

        editText.setRawInputType(InputType.TYPE_CLASS_TEXT)
        editText.setTextIsSelectable(true)
        editText.showSoftInputOnFocus = false

        ic = editText.onCreateInputConnection(EditorInfo())
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

    private fun initSheetKeyboard() {
        btnPi = binding.btnPi
        btnBracketOpen = binding.btnBracketOpen
        btnBracketClose = binding.btnBracketClose
        btnRoot = binding.btnRoot
        btnSin = binding.btnSin
        btnCos = binding.btnCos
        btnTan = binding.btnTan
        btnCot = binding.btnCot
        btnLog = binding.btnLog

        btnPi.setOnClickListener(this)
        btnBracketOpen.setOnClickListener(this)
        btnBracketClose.setOnClickListener(this)
        btnRoot.setOnClickListener(this)
        btnSin.setOnClickListener(this)
        btnCos.setOnClickListener(this)
        btnTan.setOnClickListener(this)
        btnCot.setOnClickListener(this)
        btnLog.setOnClickListener(this)

        keyValues.put(btnPi.id, getString(R.string.btn_pi))
        keyValues.put(btnBracketOpen.id, getString(R.string.btn_bracket_open))
        keyValues.put(btnBracketClose.id, getString(R.string.btn_bracket_close))
        keyValues.put(btnRoot.id, getString(R.string.btn_root) + "(")
        keyValues.put(btnSin.id, getString(R.string.btn_sin) + "(")
        keyValues.put(btnCos.id, getString(R.string.btn_cos) + "(")
        keyValues.put(btnTan.id, getString(R.string.btn_tan) + "(")
        keyValues.put(btnCot.id, getString(R.string.btn_cot) + "(")
        keyValues.put(btnLog.id, getString(R.string.btn_log) + "(")
    }

    override fun onStop() {
        super.onStop()
        if (themeDialog.isStateSaved || themeDialog.isAdded || themeDialog.isVisible) {
            themeDialog.dismissAllowingStateLoss()
        }
    }

    override fun onClick(v: View) {
        ic.commitText(keyValues[v.id], 1)
    }
}