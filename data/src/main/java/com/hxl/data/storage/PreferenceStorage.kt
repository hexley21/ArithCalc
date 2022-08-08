package com.hxl.data.storage

/**
 * Preference Storage interface for handling preference-fields by each data type.
 */
interface PreferenceStorage {

    fun save(key: String , value: Int)

    fun get(key: String, default: Int): Int
}