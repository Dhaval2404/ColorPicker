/**
 * Copyright 2018 Hong Duan
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.github.dhaval2404.colorpicker.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.github.dhaval2404.colorpicker.ColorPickerView.Companion.COLOR_POINTER_RADIUS_DP
import com.github.dhaval2404.colorpicker.R

/**
 * This file was taken from
 *      https://raw.githubusercontent.com/duanhong169/ColorPicker/master/colorpicker/src/main/java/top/defaults/colorpicker/ColorWheelSelector.java
 *
 * Color Picker Pointer
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class ColorPointer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {

    private var pointerRadius = COLOR_POINTER_RADIUS_DP
    private var point = PointF()

    init {
        alpha = 0.5f
    }

    private var selectorPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        .apply {
            color = ContextCompat.getColor(context, R.color.colorPointer)
            style = Paint.Style.STROKE
            strokeWidth = 8f
        }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(point.x, point.y, pointerRadius * 0.66f, selectorPaint)
    }

    fun setPointerRadius(pointerRadius: Float) {
        this.pointerRadius = pointerRadius
    }

    fun setCurrentPoint(point: PointF) {
        this.point = point
        invalidate()
    }
}
