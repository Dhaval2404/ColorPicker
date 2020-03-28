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
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RadialGradient
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import android.view.View

/**
 * This file was taken from
 *      https://github.com/duanhong169/ColorPicker/raw/master/colorpicker/src/main/java/top/defaults/colorpicker/ColorWheelPalette.java
 *
 * ColorPicker View
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class ColorPalette @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyle, defStyleRes) {

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private var huePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var saturationPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var hueColors = intArrayOf(
        Color.RED,
        Color.MAGENTA,
        Color.BLUE,
        Color.CYAN,
        Color.GREEN,
        Color.YELLOW,
        Color.RED
    )

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val netWidth = w - paddingLeft - paddingRight
        val netHeight = h - paddingTop - paddingBottom
        radius = netWidth.coerceAtMost(netHeight) * 0.5f

        if (radius < 0) return

        centerX = w * 0.5f
        centerY = h * 0.5f

        huePaint.shader = SweepGradient(centerX, centerY, hueColors, null)

        saturationPaint.shader = RadialGradient(
            centerX, centerY, radius,
            Color.WHITE, 0x00FFFFFF, Shader.TileMode.CLAMP
        )
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(centerX, centerY, radius, huePaint)
        canvas.drawCircle(centerX, centerY, radius, saturationPaint)
    }
}
