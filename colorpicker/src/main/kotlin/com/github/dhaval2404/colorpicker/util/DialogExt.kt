package com.github.dhaval2404.colorpicker.util

import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.R

/**
 * AlertDialog Extensions
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 28 March 2020
 */

/**
 * Set AlertDialog Button Text Color
 */
fun AlertDialog.setButtonTextColor() {
    val positiveTextColor = ContextCompat.getColor(context, R.color.positiveButtonTextColor)
    getButton(DialogInterface.BUTTON_POSITIVE)?.setTextColor(positiveTextColor)

    val negativeTextColor = ContextCompat.getColor(context, R.color.negativeButtonTextColor)
    getButton(DialogInterface.BUTTON_NEGATIVE)?.setTextColor(negativeTextColor)
}
