package com.hxl.data.storage.sharedpref

import android.content.Context
import android.content.SharedPreferences
import com.hxl.data.storage.PreferenceStorage

/**
 * Preference Storage implementation using Shared Preferences for handling preference-fields.
 */
class SharedPreferenceStorage(context: Context) : PreferenceStorage {
    private var sharedPreferences: SharedPreferences =
        context.getSharedPreferences("preferences", Context.MODE_PRIVATE)

    override fun save(key: String, value: Int) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun get(key: String, default: Int): Int {
        return sharedPreferences.getInt(key, default)
    }

}