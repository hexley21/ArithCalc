package com.hxl.domain.repository

/**
 * Preference Repository interface for handling preference fields.
 */
interface PreferenceRepository{

    fun getTheme(default: Int = -1): Int

    fun saveTheme(value: Int)

}