package com.github.dhaval2404.colorpicker.sample.util

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent

object IntentUtil {

    fun openURL(activity: Activity, url: String) {
        val link = Uri.parse(url)
        CustomTabsIntent.Builder()
            .build()
            .launchUrl(activity, link)
    }
}
