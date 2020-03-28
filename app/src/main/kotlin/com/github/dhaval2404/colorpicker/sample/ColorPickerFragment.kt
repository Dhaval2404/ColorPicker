package com.github.dhaval2404.colorpicker.sample

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.dhaval2404.colorpicker.ColorPickerDialog
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.github.dhaval2404.colorpicker.util.SharedPref
import kotlinx.android.synthetic.main.fragment_color_picker.*

/**
 * ColorPicker Demo
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 26 Dec 2019
 */
class ColorPickerFragment : Fragment() {

    private var mColor = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_color_picker, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val primaryColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
        mColor = SharedPref(context!!).getRecentColor(primaryColor)
        colorPickerBtn.setOnClickListener { _ ->
            ColorPickerDialog
                .Builder(activity!!) // Pass Activity Instance
                .setColorShape(ColorShape.SQAURE) // Or ColorShape.CIRCLE
                .setDefaultColor(mColor) // Pass Default Color
                .setColorListener { color, _ ->
                    mColor = color
                    colorPickerView.setColor(color)
                    setButtonBackground(colorPickerBtn, color)
                }
                .show()
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
