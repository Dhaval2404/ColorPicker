package com.github.dhaval2404.colorpicker.util

import android.content.Context
import android.content.SharedPreferences
import org.json.JSONArray
import java.util.Collections

/**
 * Manage SharedPreferences
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 26 Dec 2019
 */
class SharedPref(context: Context) {

    companion object {
        const val RECENT_COLORS_LIMIT = 10

        private const val SHARED_PREF_NAME = "com.github.dhaval2404.colorpicker"
        private const val KEY_RECENT_COLORS = "recent_colors"
    }

    private val manager: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)

    fun getRecentColor(defaultColor: Int): Int {
        val recentColor = getRecentColor(ColorUtil.formatColor(defaultColor))
        return ColorUtil.parseColor(recentColor)
    }

    fun getRecentColor(defaultColor: String): String {
        val recentColor = getRecentColors()
        return if (recentColor.isNotEmpty()) {
            recentColor.first()
        } else {
            defaultColor
        }
    }

    fun getRecentColors(): List<String> {
        // Get Previous Colors
        val json = get(KEY_RECENT_COLORS)

        // Return empty list if no colors found
        if (json.isNullOrBlank()) {
            return Collections.emptyList()
        }

        // Convert JSONArray to ArrayList<String>
        return jsonArrayToArrayList(JSONArray(json))
    }

    fun addColor(color: String) {
        // Get Previous Colors
        val colors = getRecentColors().toMutableList()

        // Remove color if already exist
        val first = colors.indexOfFirst {
            // Check if color is nearby
            ColorUtil.isEqualColor(color, it)
        }

        if (first >= 0) {
            colors.removeAt(first)
        }

        // Remove last color if recent color list is full
        if (colors.size >= RECENT_COLORS_LIMIT) {
            colors.removeAt(colors.size - 1)
        }

        // Add New Color
        colors.add(0, color)

        // Convert List to Json
        val json = JSONArray(colors).toString()

        // Save RecentColor json
        put(KEY_RECENT_COLORS, json)
    }

    private fun get(key: String, default: String? = null) = manager.getString(key, default)

    private fun put(key: String, value: Any?) {
        val editor = manager.edit()
        when (value) {
            is Boolean -> editor.putBoolean(key, value)
            is Int -> editor.putInt(key, value)
            is Float -> editor.putFloat(key, value)
            is Long -> editor.putLong(key, value)
            is String -> editor.putString(key, value)
            is Enum<*> -> editor.putString(key, value.toString())
            null -> editor.remove(key)
            else -> throw RuntimeException("Attempting to save non-supported preference")
        }
        editor.apply()
    }

    private fun jsonArrayToArrayList(jsonArray: JSONArray): List<String> {
        val arrayList = ArrayList<String>()
        for (i in 0 until jsonArray.length()) {
            arrayList.add(jsonArray.getString(i))
        }
        return arrayList
    }
}
