package com.hxl.arithcalc.presentation.fragment.calculator.keyboard

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.ExtractedTextRequest
import android.view.inputmethod.InputConnection
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.hxl.arithcalc.R
import com.hxl.arithcalc.databinding.KeyboardCalculatorBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder
import java.text.DecimalFormat

class KeyboardCalculator(context: Context, attrs: AttributeSet) :
LinearLayout(context, attrs, 0), View.OnClickListener {
    private var binding: KeyboardCalculatorBinding

    private var buttonBackSpace: Button
    private var buttonClear: Button
    private var buttonPower: Button
    private var buttonDivision: Button
    private var buttonMultiplication: Button
    private var buttonSubtraction: Button
    private var buttonAddition: Button
    private var buttonOne: Button
    private var buttonTwo: Button
    private var buttonThree: Button
    private var buttonFour: Button
    private var buttonFive: Button
    private var buttonSix: Button
    private var buttonSeven: Button
    private var buttonEight: Button
    private var buttonNine: Button
    private var buttonZero: Button
    private var buttonPercent: Button
    private var buttonComma: Button
    private var buttonEvaluate: Button

    private val keyValues = SparseArray<String>()
    private lateinit var inputConnection: InputConnection
    private lateinit var resultField: TextView

    private val operatorArray = arrayOf('+', '-', '×', '÷')
    private val scientificArray = arrayOf("√(", "sin(", "cos(", "tan(", "cot(", "log(")
    private val numberArray = arrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')

    private var isEvaluated = false
    private var operatorList: MutableList<Int> = mutableListOf()
    private var commaList: MutableList<Int> = mutableListOf()
    private var numberList: MutableList<Int> = mutableListOf()

    init {
        val layoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = KeyboardCalculatorBinding.inflate(layoutInflater, this, true)
        buttonBackSpace = binding.buttonBackspace
        buttonClear = binding.buttonClear
        buttonPower = binding.buttonPower
        buttonDivision = binding.buttonDivision
        buttonMultiplication = binding.buttonMultiplication
        buttonSubtraction = binding.buttonSubtraction
        buttonAddition = binding.buttonAddition
        buttonOne = binding.buttonOne
        buttonTwo = binding.buttonTwo
        buttonThree = binding.buttonThree
        buttonFour = binding.buttonFour
        buttonFive = binding.buttonFive
        buttonSix = binding.buttonSix
        buttonSeven = binding.buttonSeven
        buttonEight = binding.buttonEight
        buttonNine = binding.buttonNine
        buttonZero = binding.buttonZero
        buttonPercent = binding.buttonPercent
        buttonComma = binding.buttonComma
        buttonEvaluate = binding.buttonEvaluate

        buttonBackSpace.setOnClickListener(this)
        buttonClear.setOnClickListener(this)
        buttonPower.setOnClickListener(this)
        buttonDivision.setOnClickListener(this)
        buttonMultiplication.setOnClickListener(this)
        buttonSubtraction.setOnClickListener(this)
        buttonAddition.setOnClickListener(this)
        buttonOne.setOnClickListener(this)
        buttonTwo.setOnClickListener(this)
        buttonThree.setOnClickListener(this)
        buttonFour.setOnClickListener(this)
        buttonFive.setOnClickListener(this)
        buttonSix.setOnClickListener(this)
        buttonSeven.setOnClickListener(this)
        buttonEight.setOnClickListener(this)
        buttonNine.setOnClickListener(this)
        buttonZero.setOnClickListener(this)
        buttonPercent.setOnClickListener(this)
        buttonComma.setOnClickListener(this)
        buttonEvaluate.setOnClickListener(this)

        keyValues.put(buttonPower.id, getStringResource(R.string.btn_pow))
        keyValues.put(buttonOne.id, getStringResource(R.string.btn_one))
        keyValues.put(buttonTwo.id, getStringResource(R.string.btn_two))
        keyValues.put(buttonThree.id, getStringResource(R.string.btn_three))
        keyValues.put(buttonFour.id, getStringResource(R.string.btn_four))
        keyValues.put(buttonFive.id, getStringResource(R.string.btn_five))
        keyValues.put(buttonSix.id, getStringResource(R.string.btn_six))
        keyValues.put(buttonSeven.id, getStringResource(R.string.btn_seven))
        keyValues.put(buttonEight.id, getStringResource(R.string.btn_eight))
        keyValues.put(buttonNine.id, getStringResource(R.string.btn_nine))
        keyValues.put(buttonZero.id, getStringResource(R.string.btn_zero))
        keyValues.put(buttonPercent.id, getStringResource(R.string.btn_percent))
        keyValues.put(buttonComma.id, getStringResource(R.string.btn_comma))
    }

    override fun onClick(v: View) {
        when (v.id) {
            buttonBackSpace.id -> onBackspace()
            buttonClear.id -> onClear()
            buttonEvaluate.id -> onEval()
            buttonAddition.id -> onOperator(operatorArray[0])
            buttonSubtraction.id -> onOperator(operatorArray[1])
            buttonMultiplication.id -> onOperator(operatorArray[2])
            buttonDivision.id -> onOperator(operatorArray[3])
            buttonComma.id -> onComma()
            else -> onElse(v.id)
        }
    }

    private fun onBackspace() {
        if (TextUtils.isEmpty(inputConnection.getSelectedText(0))) {
            inputConnection.deleteSurroundingText(1, 0)
        } else {
            inputConnection.commitText("", 1)
        }
    }

    private fun onClear() {
        val currentText = getText()
        val beforeCursorText = inputConnection.getTextBeforeCursor(currentText.length, 0)
        val afterCursorText = inputConnection.getTextAfterCursor(currentText.length, 0)
        inputConnection.deleteSurroundingText(beforeCursorText!!.length, afterCursorText!!.length)
        resultField.text = ""
        operatorList = mutableListOf()
        commaList = mutableListOf()
        isEvaluated = false
    }

    private fun onElse(id: Int) {
        commitText(keyValues[id])
    }

    private fun onOperator(op: Char) {
        operatorList.add(getText().length)
        commitText(op.toString())
    }

    private fun onComma() {
        commaList.add(getText().length)
        commitText(",")
    }

    private fun onEval() {
        if (!isEvaluated) {
            val text = getText().toString()
                .replace("%", "pr")
                .replace('×', '*')
                .replace('÷', '/')
                .replace(',', '.')
            try {
                val expression: Expression = ExpressionBuilder(text)
                    .variable("pr")
                    .build()
                    .setVariable("pr", 0.01)
                val df = DecimalFormat("#")
                df.maximumFractionDigits = 8

                val result = df.format(expression.evaluate()).toString()
                    .replace('.', ',')
                setResult(result)
                isEvaluated = true
            } catch (e: Exception) {
                setResult("${e.message}")
            }
        } else {
            val result = resultField.text
            onClear()
            commitText(result.toString(), 1)
        }
    }

    private fun getText(): CharSequence {
        return inputConnection.getExtractedText(ExtractedTextRequest(), 0).text
    }

    private fun getStringResource(id: Int): String {
        return context.resources.getString(id)
    }

    private fun commitText(text: String, position: Int = 1) {
        inputConnection.commitText(text, position)
    }

    private fun setResult(text: String) {
        resultField.text = text
    }

    fun setConnection(ic: InputConnection?, textView: TextView) {
        inputConnection = ic!!
        resultField = textView
    }
}