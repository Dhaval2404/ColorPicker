package com.github.dhaval2404.colorpicker

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.adapter.MaterialColorPickerAdapter
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.google.android.flexbox.FlexboxLayoutManager

/**
 * Color Picker from Predefined color set in AlertDialog
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class MaterialColorPickerDialog private constructor(
    val context: Context,
    val title: String,
    val positiveButton: String,
    val negativeButton: String,
    val colorListener: ColorListener?,
    val defaultColor: String?,
    val colorSwatch: ColorSwatch,
    var colorShape: ColorShape,
    val colors: List<String>? = null
) {
    data class Builder(
        val context: Context,
        var title: String = context.getString(R.string.material_dialog_title),
        var positiveButton: String = context.getString(R.string.material_dialog_positive_button),
        var negativeButton: String = context.getString(R.string.material_dialog_negative_button),
        var colorListener: ColorListener? = null,
        var defaultColor: String? = null,
        var colorSwatch: ColorSwatch = ColorSwatch._300,
        var colorShape: ColorShape = ColorShape.CIRCLE,
        var colors: List<String>? = null
    ) {
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
         * Set Color Swatch
         *
         * @param colorSwatch ColorSwatch
         */
        fun setColorSwatch(colorSwatch: ColorSwatch): Builder {
            this.colorSwatch = colorSwatch
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
         * Provide PreDefined Colors,
         *
         * If colors is not empty, User can choose colors from provided list
         * If colors is empty, User can choose colors based on ColorSwatch
         *
         * @param colors List<String> List of Hex Colors
         */
        fun setColors(colors: List<String>): Builder {
            this.colors = colors
            return this
        }

        /**
         * Provide PreDefined Colors,
         *
         * If colors is not empty, User can choose colors from provided list
         * If colors is empty, User can choose colors based on ColorSwatch
         *
         * @param colors List<Int> List of Color Resource
         */
        fun setColorRes(colors: List<Int>): Builder {
            this.colors = colors.map { ColorUtil.formatColor(it) }
            return this
        }

        /**
         * Creates an {@link MaterialColorPickerDialog} with the arguments supplied to this
         * builder.
         * <p>
         * Calling this method does not display the dialog. If no additional
         * processing is needed, {@link #show()} may be called instead to both
         * create and display the dialog.
         */
        fun build(): MaterialColorPickerDialog {
            return MaterialColorPickerDialog(
                context = context,
                title = title,
                positiveButton = positiveButton,
                negativeButton = negativeButton,
                colorListener = colorListener,
                defaultColor = defaultColor,
                colorShape = colorShape,
                colorSwatch = colorSwatch,
                colors = colors
            )
        }

        /**
         * Show Alert Dialog
         */
        fun show() {
            build().show()
        }

        /**
         * Show BottomSheet Dialog
         */
        fun showBottomSheet(fragmentManager: FragmentManager) {
            build().showBottomSheet(fragmentManager)
        }
    }

    /**
     * Show BottomSheet Dialog
     */
    fun showBottomSheet(fragmentManager: FragmentManager) {
        MaterialColorPickerBottomSheet.getInstance(this)
            .setColorListener(colorListener)
            .show(fragmentManager, "")
    }

    /**
     * Show AlertDialog
     */
    fun show() {
        // Create Dialog Instance
        val dialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setNegativeButton(negativeButton, null)

        // Create Custom View for Dialog
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val dialogView = inflater.inflate(R.layout.dialog_material_color_picker, null) as View
        dialog.setView(dialogView)

        // Setup Color Listing Adapter
        val colorList = colors ?: ColorUtil.getColors(context, colorSwatch.value)
        val adapter = MaterialColorPickerAdapter(colorList)
        adapter.setColorShape(colorShape)
        if (!defaultColor.isNullOrBlank()) {
            adapter.setDefaultColor(defaultColor)
        }

        // Setup Color RecyclerView
        val materialColorRV = dialogView.findViewById<RecyclerView>(R.id.materialColorRV)
        materialColorRV.setHasFixedSize(true)
        materialColorRV.layoutManager = FlexboxLayoutManager(context)
        materialColorRV.adapter = adapter

        // Set Submit Click Listener
        dialog.setPositiveButton(positiveButton) { _, _ ->
            val color = adapter.getSelectedColor()
            if (color.isNotBlank()) {
                colorListener?.onColorSelected(ColorUtil.parseColor(color), color)
            }
        }

        // Show Dialog
        dialog.show()
    }
}
