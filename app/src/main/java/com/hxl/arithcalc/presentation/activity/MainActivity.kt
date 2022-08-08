package com.hxl.arithcalc.presentation.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hxl.arithcalc.presentation.fragment.calculator.CalculatorFragment
import com.hxl.arithcalc.R
import com.hxl.data.repository.PreferenceRepositoryImpl
import com.hxl.data.storage.sharedpref.SharedPreferenceStorage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context) {
        val sharedPref = PreferenceRepositoryImpl(SharedPreferenceStorage(newBase))
        AppCompatDelegate.setDefaultNightMode(sharedPref.theme)
        super.attachBaseContext(newBase)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply { setKeepOnScreenCondition { false } }
        setContentView(R.layout.activity_main)
        replaceFragment(CalculatorFragment())
    }

    fun replaceFragment(fragment: Fragment, backStack: Boolean = false) {
        val backStateName = fragment.javaClass.name
        val manager: FragmentManager = supportFragmentManager
        val fragmentPopped: Boolean = manager.popBackStackImmediate(backStateName, 0)
        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) {
            // create fragment, if not in backstack
            val ft: FragmentTransaction = manager.beginTransaction()
            ft.replace(R.id.main_container, fragment)
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            if (!backStack) {
                manager.popBackStack()
            }
            ft.addToBackStack(backStateName)
            ft.commit()
        }
    }
}