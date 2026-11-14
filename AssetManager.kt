package com.freavity.androidengine.engine.assets

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

class AssetManager(context: Context) {
    private val assets = context.applicationContext.assets
    private val bitmapCache = mutableMapOf<String, Bitmap>()

    fun loadBitmap(name: String): Bitmap? {
        bitmapCache[name]?.let { return it }
        return try {
            assets.open(name).use { input ->
                BitmapFactory.decodeStream(input)?.also { bitmapCache[name] = it }
            }
        } catch (_: Exception) {
            null
        }
    }

    fun readText(name: String): String? = try {
        assets.open(name).bufferedReader().use { it.readText() }
    } catch (_: Exception) {
        null
    }

    fun clearCache() {
        bitmapCache.values.forEach { it.recycle() }
        bitmapCache.clear()
    }
}
