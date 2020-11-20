package com.github.dhaval2404.colorpicker.sample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.model.ColorSwatch
import com.github.dhaval2404.colorpicker.util.ColorUtil
import kotlinx.android.synthetic.main.fragment_material_color_picker.*

/**
 * MaterialColorPicker Demo
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 26 Dec 2019
 */
class MaterialColorPickerFragment : Fragment() {

    private var mMaterialColorSquare: String = ""
    private var mMaterialColorCircle = ""
    private var mMaterialColorBottomSheet = ""
    private var mMaterialPreDefinedColor = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_material_color_picker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        materialDialogPickerSquareBtn.setOnClickListener { _ ->
            MaterialColorPickerDialog
                .Builder(requireActivity()) // Pass Activity Instance
                .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
                .setColorSwatch(ColorSwatch._300) // Default ColorSwatch._500
                .setDefaultColor(mMaterialColorSquare) // Pass Default Color
                .setColorListener { color, colorHex ->
                    mMaterialColorSquare = colorHex
                    setButtonBackground(materialDialogPickerSquareBtn, color)
                }
                .show()
        }

        materialDialogPickerCircleBtn.setOnClickListener { _ ->
            MaterialColorPickerDialog
                .Builder(requireActivity())
                .setColorSwatch(ColorSwatch._500)
                .setDefaultColor(mMaterialColorCircle)
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        mMaterialColorCircle = colorHex
                        setButtonBackground(materialDialogPickerCircleBtn, color)
                    }
                })
                .show()
        }

        materialBottomSheetDialogBtn.setOnClickListener { _ ->
            MaterialColorPickerDialog
                .Builder(requireActivity())
                .setColorSwatch(ColorSwatch._300)
                .setDefaultColor(mMaterialColorBottomSheet)
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        mMaterialColorBottomSheet = colorHex
                        setButtonBackground(materialBottomSheetDialogBtn, color)
                    }
                })
                .showBottomSheet(childFragmentManager)
        }

        materialPreDefinedColorPickerBtn.setOnClickListener { _ ->
            MaterialColorPickerDialog
                .Builder(requireActivity())
                .setColors(arrayListOf("#f6e58d", "#ffbe76", "#ff7979", "#badc58", "#dff9fb", "#7ed6df", "#e056fd", "#686de0", "#30336b", "#95afc0"))
                // .setColors(resources.getStringArray(R.array.themeColorHex))
                .setColorRes(resources.getIntArray(R.array.themeColors))
                .setDefaultColor(mMaterialPreDefinedColor)
                .setColorListener(object : ColorListener {
                    override fun onColorSelected(color: Int, colorHex: String) {
                        mMaterialPreDefinedColor = colorHex
                        setButtonBackground(materialPreDefinedColorPickerBtn, color)
                    }
                })
                .showBottomSheet(childFragmentManager)
        }
    }

    private fun setButtonBackground(btn: AppCompatButton, color: Int) {
        if (ColorUtil.isDarkColor(color)) {
            btn.setTextColor(Color.WHITE)
        } else {
            btn.setTextColor(Color.BLACK)
        }
        btn.backgroundTintList = ColorStateList.valueOf(color)
    }
}
