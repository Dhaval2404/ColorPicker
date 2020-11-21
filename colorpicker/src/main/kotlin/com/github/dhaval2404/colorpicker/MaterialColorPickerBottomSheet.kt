package com.github.dhaval2404.colorpicker

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.dhaval2404.colorpicker.adapter.MaterialColorPickerAdapter
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.listener.DismissListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_bottomsheet_material_color_picker.*

/**
 * Color Picker from Predefined color set in BottomSheetDialogFragment
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 24 Dec 2019
 */
class MaterialColorPickerBottomSheet : BottomSheetDialogFragment() {

    private var title: String? = null
    private var positiveButton: String? = null
    private var negativeButton: String? = null
    private var colorListener: ColorListener? = null
    private var dismissListener: DismissListener? = null
    private var defaultColor: String? = null
    private var colorShape: ColorShape = ColorShape.CIRCLE
    private var colorSwatch: ColorSwatch = ColorSwatch._300
    private var colors: List<String>? = null

    companion object {

        private const val EXTRA_TITLE = "extra.title"
        private const val EXTRA_POSITIVE_BUTTON = "extra.positive_Button"
        private const val EXTRA_NEGATIVE_BUTTON = "extra.negative_button"

        private const val EXTRA_DEFAULT_COLOR = "extra.default_color"
        private const val EXTRA_COLOR_SHAPE = "extra.color_shape"
        private const val EXTRA_COLOR_SWATCH = "extra.color_swatch"
        private const val EXTRA_COLORS = "extra.colors"

        fun getInstance(dialog: MaterialColorPickerDialog): MaterialColorPickerBottomSheet {
            val bundle = Bundle().apply {
                putString(EXTRA_TITLE, dialog.title)
                putString(EXTRA_POSITIVE_BUTTON, dialog.positiveButton)
                putString(EXTRA_NEGATIVE_BUTTON, dialog.negativeButton)

                putString(EXTRA_DEFAULT_COLOR, dialog.defaultColor)
                putParcelable(EXTRA_COLOR_SWATCH, dialog.colorSwatch)
                putParcelable(EXTRA_COLOR_SHAPE, dialog.colorShape)

                var list: ArrayList<String>? = null
                if (dialog.colors != null) {
                    list = ArrayList(dialog.colors)
                }
                putStringArrayList(EXTRA_COLORS, list)
            }

            return MaterialColorPickerBottomSheet().apply {
                arguments = bundle
            }
        }
    }

    fun setColorListener(listener: ColorListener?): MaterialColorPickerBottomSheet {
        this.colorListener = listener
        return this
    }

    fun setDismissListener(listener: DismissListener?): MaterialColorPickerBottomSheet {
        this.dismissListener = listener
        return this
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_bottomsheet_material_color_picker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        arguments?.let {
            title = it.getString(EXTRA_TITLE)
            positiveButton = it.getString(EXTRA_POSITIVE_BUTTON)
            negativeButton = it.getString(EXTRA_NEGATIVE_BUTTON)

            defaultColor = it.getString(EXTRA_DEFAULT_COLOR)
            colorSwatch = it.getParcelable(EXTRA_COLOR_SWATCH)!!
            colorShape = it.getParcelable(EXTRA_COLOR_SHAPE)!!

            colors = it.getStringArrayList(EXTRA_COLORS)
        }

        title?.let { titleTxt.text = it }
        positiveButton?.let { positiveBtn.text = it }
        negativeButton?.let { negativeBtn.text = it }

        val colorList = colors ?: ColorUtil.getColors(context!!, colorSwatch.value)
        val adapter = MaterialColorPickerAdapter(colorList)
        adapter.setColorShape(colorShape)
        if (!defaultColor.isNullOrBlank()) {
            adapter.setDefaultColor(defaultColor!!)
        }

        materialColorRV.setHasFixedSize(true)
        materialColorRV.layoutManager = FlexboxLayoutManager(context)
        materialColorRV.adapter = adapter

        positiveBtn.setOnClickListener {
            val color = adapter.getSelectedColor()
            if (color.isNotBlank()) {
                colorListener?.onColorSelected(ColorUtil.parseColor(color), color)
            }
            dismiss()
        }
        negativeBtn.setOnClickListener { dismiss() }
    }

    override fun dismiss() {
        super.dismiss()
        dismissListener?.onDismiss()
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        dismissListener?.onDismiss()
    }

}
