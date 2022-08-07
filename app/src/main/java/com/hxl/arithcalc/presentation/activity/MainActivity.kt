package com.hxl.arithcalc.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.hxl.arithcalc.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply { setKeepOnScreenCondition { false } }
        setContentView(R.layout.activity_main)
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