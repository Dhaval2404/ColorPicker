package com.github.dhaval2404.colorpicker.util

import android.content.Context

/**
 * Android Asset Folder related utility functions
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 24 Dec 2019
 */
object AssetUtil {

    /**
     * Read asset file content
     *
     * @param context Application Context
     * @param fileName Asset file name
     * @return String file content
     */
    fun readAssetFile(context: Context, fileName: String): String {
        return context.assets.open(fileName)
            .bufferedReader().use {
                it.readText()
            }
    }
}
