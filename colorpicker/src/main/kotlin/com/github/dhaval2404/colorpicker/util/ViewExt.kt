package com.github.dhaval2404.colorpicker.util

import android.view.View

/**
 * View Extensions
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 28 March 2020
 */

fun View.setVisibility(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}
