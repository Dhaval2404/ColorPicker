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
import com.github.dhaval2404.colorpicker.listener.DismissListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.github.dhaval2404.colorpicker.util.setButtonTextColor
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
    val dismissListener: DismissListener?,
    val defaultColor: String?,
    val colorSwatch: ColorSwatch,
    var colorShape: ColorShape,
    val colors: List<String>? = null,
    var isTickColorPerCard: Boolean = false
) {

    class Builder(val context: Context) {

        private var title: String = context.getString(R.string.material_dialog_title)
        private var positiveButton: String = context.getString(R.string.material_dialog_positive_button)
        private var negativeButton: String = context.getString(R.string.material_dialog_negative_button)
        private var colorListener: ColorListener? = null
        private var dismissListener: DismissListener? = null
        private var defaultColor: String? = null
        private var colorSwatch: ColorSwatch = ColorSwatch._300
        private var colorShape: ColorShape = ColorShape.CIRCLE
        private var colors: List<String>? = null
        private var isTickColorPerCard: Boolean = false

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

        fun setColors(colors: Array<String>): Builder {
            this.colors = colors.toList()
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

        fun setColorRes(colors: IntArray): Builder {
            this.colors = colors.map { ColorUtil.formatColor(it) }
            return this
        }

        /**
         * Set tick icon color, Default will be false
         *
         * If false,
         *     First the majority of color(dark/light) will be calculated
         *     If dark color count > light color count
         *          tick color will be WHITE
         *     else
         *          tick color will be BLACK
         *     Here, Tick color will be same card,
         *     Which might create issue with black and white color in list
         *
         * If true,
         *      based on the each color(dark/light) the card tick color will be decided
         *      Here, Tick color will be different for each card
         *
         * @param tickColorPerCard Boolean
         */
        fun setTickColorPerCard(tickColorPerCard: Boolean): Builder {
            this.isTickColorPerCard = tickColorPerCard
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
                dismissListener = dismissListener,
                defaultColor = defaultColor,
                colorShape = colorShape,
                colorSwatch = colorSwatch,
                colors = colors,
                isTickColorPerCard = isTickColorPerCard
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
            .setDismissListener(dismissListener)
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
        adapter.setTickColorPerCard(isTickColorPerCard)
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

        dismissListener?.let { listener ->
            dialog.setOnDismissListener {
                listener.onDismiss()
            }
        }

        // Create AlertDialog
        val alertDialog = dialog.create()

        // Show Dialog
        alertDialog.show()

        alertDialog.setButtonTextColor()
    }
}
