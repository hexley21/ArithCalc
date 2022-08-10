package com.hxl.arithcalc.presentation.fragment.dialogs.theme

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.hxl.arithcalc.R
import com.hxl.arithcalc.presentation.fragment.dialogs.PopDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThemeDialog : PopDialog() {
    companion object {
        const val TAG: String = "theme_dialog"
    }

    private val vm: ThemeViewModel by viewModels()
    private val config: Int = AppCompatDelegate.getDefaultNightMode()
    private val modeArray: Array<Int> = arrayOf(1, 2, -1)
    override var checkedItem: Int = modeArray.indexOf(config)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        title = resources.getString(R.string.select_theme)
        choice = resources.getStringArray(R.array.theme_choice)
    }

    // Saves and applies new Theme to the app
    @RequiresApi(Build.VERSION_CODES.S)
    override fun positiveListener(checkedItem: Int) {
        if (checkedItem != modeArray.indexOf(config)) {
            vm.theme = modeArray[checkedItem]
            AppCompatDelegate.setDefaultNightMode(modeArray[checkedItem])
        }
    }
}