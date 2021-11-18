package com.futuraeducation.utils

import android.content.Context
import android.content.SharedPreferences

class MyPreferences(internal var context: Context?) {

    lateinit var sharedPreferences: SharedPreferences

    init {
        if (context != null) {
            sharedPreferences = this.context!!.getSharedPreferences("vpnews24", 0)
        }
    }

    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    fun getString(key: String, value: String): String? {
        return sharedPreferences.getString(key, value)
    }

    fun setString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getInt(key: String): Int {
        return sharedPreferences.getInt(key, 0)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun setInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }


    fun getFloat(key: String): Float {
        return sharedPreferences.getFloat(key, 0f)
    }

    fun getFloat(key: String, defaultValue: Float): Float {
        return sharedPreferences.getFloat(key, defaultValue)
    }

    fun setFloat(key: String, value: Float) {
        val editor = sharedPreferences.edit()
        editor.putFloat(key, value)
        editor.apply()
    }


    fun getLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    fun getLong(key: String, defaultValue: Long): Long {
        return sharedPreferences.getLong(key, defaultValue)
    }

    fun setLong(key: String, value: Long) {
        val editor = sharedPreferences.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    fun setBoolean(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun clearAllData(){
        sharedPreferences.edit().clear().apply()
    }


}
