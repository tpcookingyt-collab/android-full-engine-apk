package com.freavity.androidengine.engine.storage

import android.content.Context
import android.content.SharedPreferences

class SaveManager(context: Context) {
    private val prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences("engine_save", Context.MODE_PRIVATE)

    fun putInt(key: String, value: Int) { prefs.edit().putInt(key, value).apply() }
    fun getInt(key: String, default: Int = 0): Int = prefs.getInt(key, default)
    fun putFloat(key: String, value: Float) { prefs.edit().putFloat(key, value).apply() }
    fun getFloat(key: String, default: Float = 0f): Float = prefs.getFloat(key, default)
    fun putString(key: String, value: String) { prefs.edit().putString(key, value).apply() }
    fun getString(key: String, default: String = ""): String = prefs.getString(key, default) ?: default
    fun putBoolean(key: String, value: Boolean) { prefs.edit().putBoolean(key, value).apply() }
    fun getBoolean(key: String, default: Boolean = false): Boolean = prefs.getBoolean(key, default)

    fun highScore(): Int = getInt("high_score", 0)
    fun saveHighScore(score: Int) {
        if (score > highScore()) putInt("high_score", score)
    }
    fun clear() { prefs.edit().clear().apply() }
}
