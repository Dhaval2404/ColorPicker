package com.github.dhaval2404.colorpicker

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.adapter.RecentColorAdapter
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.listener.DismissListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.github.dhaval2404.colorpicker.util.SharedPref
import com.github.dhaval2404.colorpicker.util.setButtonTextColor
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.card.MaterialCardView

/**
 * Color Picker Dialog, Pick Color from Color Dial
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class ColorPickerDialog private constructor(
    val context: Context,
    val title: String,
    val positiveButton: String,
    val negativeButton: String,
    val colorListener: ColorListener?,
    val dismissListener: DismissListener?,
    val defaultColor: String?,
    var colorShape: ColorShape
) {

    class Builder(val context: Context) {
        private var title: String = context.getString(R.string.material_dialog_title)
        private var positiveButton: String = context.getString(R.string.material_dialog_positive_button)
        private var negativeButton: String = context.getString(R.string.material_dialog_negative_button)
        private var colorListener: ColorListener? = null
        private var dismissListener: DismissListener? = null
        private var defaultColor: String? = null
        private var colorShape: ColorShape = ColorShape.CIRCLE

        /**
         * Set Dialog Title
         *
         * @param title String
         */
        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        /**
         * Set Dialog Title
         *
         * @param title StringRes
         */
        fun setTitle(@StringRes title: Int): Builder {
            this.title = context.getString(title)
            return this
        }

        /**
         * Set Positive Button Text
         *
         * @param text String
         */
        fun setPositiveButton(text: String): Builder {
            this.positiveButton = text
            return this
        }

        /**
         * Set Positive Button Text
         *
         * @param text StringRes
         */
        fun setPositiveButton(@StringRes text: Int): Builder {
            this.positiveButton = context.getString(text)
            return this
        }

        /**
         * Set Negative Button Text
         *
         * @param text String
         */
        fun setNegativeButton(text: String): Builder {
            this.negativeButton = text
            return this
        }

        /**
         * Set Negative Button Text
         *
         * @param text StringRes
         */
        fun setNegativeButton(@StringRes text: Int): Builder {
            this.negativeButton = context.getString(text)
            return this
        }

        /**
         * Set Default Selected Color
         *
         * @param color String Hex Color
         */
        fun setDefaultColor(color: String): Builder {
            this.defaultColor = color
            return this
        }

        /**
         * Set Default Selected Color
         *
         * @param color Int ColorRes
         */
        fun setDefaultColor(@ColorRes color: Int): Builder {
            this.defaultColor = ColorUtil.formatColor(color)
            return this
        }

        /**
         * Set Color CardView Shape,
         *
         * @param colorShape ColorShape
         */
        fun setColorShape(colorShape: ColorShape): Builder {
            this.colorShape = colorShape
            return this
        }

        /**
         * Set Color Listener
         *
         * @param listener ColorListener
         */
        fun setColorListener(listener: ColorListener): Builder {
            this.colorListener = listener
            return this
        }

        /**
         * Set Color Listener
         *
         * @param listener (Int, String)->Unit
         */
        fun setColorListener(listener: (Int, String) -> Unit): Builder {
            this.colorListener = object : ColorListener {
                override fun onColorSelected(color: Int, colorHex: String) {
                    listener(color, colorHex)
                }
            }
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @param listener DismissListener
         */
        fun setDismissListener(listener: DismissListener?): Builder {
            this.dismissListener = listener
            return this
        }

        /**
         * Sets the callback that will be called when the dialog is dismissed for any reason.
         *
         * @param listener listener: () -> Unit
         */
        fun setDismissListener(listener: () -> Unit): Builder {
            this.dismissListener = object : DismissListener {
                override fun onDismiss() {
                    listener.invoke()
                }
            }
            return this
        }

        /**
         * Creates an {@link ColorPickerDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        fun build(): ColorPickerDialog {
            return ColorPickerDialog(
                context = context,
                title = title,
                positiveButton = positiveButton,
                negativeButton = negativeButton,
                colorListener = colorListener,
                dismissListener = dismissListener,
                defaultColor = defaultColor,
                colorShape = colorShape
            )
        }

        /**
         * Show Color Picker Dialog
         */
        fun show() {
            build().show()
        }
    }

    /**
     * Show Color Picker Dialog
     */
    fun show() {

        // Create Dialog Instance
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setNegativeButton(negativeButton, null)

        // Setup Custom View
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_color_picker, null) as View
        dialog.setView(dialogView)

        val colorPicker = dialogView.findViewById<ColorPickerView>(R.id.colorPicker)
        val colorView = dialogView.findViewById<MaterialCardView>(R.id.colorView)
        val recentColorsRV = dialogView.findViewById<RecyclerView>(R.id.recentColorsRV)

        val initialColor = if (!defaultColor.isNullOrBlank()) {
            Color.parseColor(defaultColor)
        } else {
            ContextCompat.getColor(context, R.color.grey_500)
        }
        colorView.setCardBackgroundColor(initialColor)

        colorPicker.setColor(initialColor)
        colorPicker.setColorListener { color, _ ->
            colorView.setCardBackgroundColor(color)
        }

        val sharedPref = SharedPref(context)
        val colorList = sharedPref.getRecentColors()

        // Setup Color Listing Adapter
        val adapter = RecentColorAdapter(colorList)
        adapter.setColorShape(colorShape)
        adapter.setColorListener(object : ColorListener {
            override fun onColorSelected(color: Int, colorHex: String) {
                colorPicker.setColor(color)
                colorView.setCardBackgroundColor(color)
            }
        })

        recentColorsRV.layoutManager = FlexboxLayoutManager(context)
        recentColorsRV.adapter = adapter

        // Set Submit Click Listener
        dialog.setPositiveButton(positiveButton) { _, _ ->
            val color = colorPicker.getColor()
            val colorHex = ColorUtil.formatColor(color)
            colorListener?.onColorSelected(color, colorHex)
            sharedPref.addColor(color = colorHex)
        }

        dismissListener?.let { listener ->
            dialog.setOnDismissListener {
                listener.onDismiss()
            }
        }

        // Create AlertDialog
        val alertDialog = dialog.create()

        // Show Dialog
        alertDialog.show()

        // Set Button Text Color
        alertDialog.setButtonTextColor()
    }
}
