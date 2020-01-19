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
package com.github.dhaval2404.colorpicker

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Color
import android.graphics.PointF
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.FrameLayout
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.util.ColorUtil
import com.github.dhaval2404.colorpicker.widget.ColorPalette
import com.github.dhaval2404.colorpicker.widget.ColorPointer
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/**
 * This file was taken from
 *     https://github.com/duanhong169/ColorPicker/raw/master/colorpicker/src/main/java/top/defaults/colorpicker/ColorWheelView.java
 *
 * ColorPicker View
 *
 * @version 1.0
 * @since 23 Dec 2019
 */
class ColorPickerView : FrameLayout {

    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    companion object {
        const val COLOR_POINTER_RADIUS_DP = 8f
    }

    private var radius = 0f
    private var centerX = 0f
    private var centerY = 0f

    private var pointerRadiusPx = COLOR_POINTER_RADIUS_DP * resources.displayMetrics.density

    private var currentColor = Color.MAGENTA
    private val currentPoint = PointF()

    private var colorPointer: ColorPointer
    private var colorListener: ColorListener? = null

    init {
        val layoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val palette = ColorPalette(context)
        val padding = pointerRadiusPx.toInt()
        palette.setPadding(padding, padding, padding, padding)
        addView(palette, layoutParams)

        colorPointer = ColorPointer(context)
        colorPointer.setPointerRadius(pointerRadiusPx)
        addView(colorPointer, layoutParams)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxWidth = MeasureSpec.getSize(widthMeasureSpec)
        val maxHeight = MeasureSpec.getSize(heightMeasureSpec)

        val size = maxWidth.coerceAtMost(maxHeight)
        super.onMeasure(
            MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY),
            MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        val netWidth = w - paddingLeft - paddingRight
        val netHeight = h - paddingTop - paddingBottom
        radius = netWidth.coerceAtMost(netHeight) * 0.5f - pointerRadiusPx

        if (radius < 0) return

        centerX = netWidth * 0.5f
        centerY = netHeight * 0.5f

        setColor(currentColor)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN,
            MotionEvent.ACTION_MOVE,
            MotionEvent.ACTION_UP -> {
                update(event)
                return true
            }
        }
        return super.onTouchEvent(event)
    }

    private fun update(event: MotionEvent) {
        val x = event.x
        val y = event.y

        currentColor = getColorAtPoint(x, y)
        pickColor(currentColor)
        updateSelector(x, y)
    }

    private fun getColorAtPoint(eventX: Float, eventY: Float): Int {
        val x = eventX - centerX
        val y = eventY - centerY
        val r = sqrt(x * x + y * y.toDouble())
        val hsv = floatArrayOf(0f, 0f, 1f)
        hsv[0] = (atan2(y.toDouble(), -x.toDouble()) / Math.PI * 180f).toFloat() + 180
        hsv[1] = 0f.coerceAtLeast(1f.coerceAtMost((r / radius).toFloat()))
        return Color.HSVToColor(hsv)
    }

    fun getColor() = currentColor

    fun setColor(color: Int) {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        val r = hsv[1] * radius
        val radian = (hsv[0] / 180f * Math.PI).toFloat()

        updateSelector(
            (r * cos(radian.toDouble()) + centerX).toFloat(),
            (-r * sin(radian.toDouble()) + centerY).toFloat()
        )

        currentColor = color
    }

    private fun updateSelector(eventX: Float, eventY: Float) {
        var x = eventX - centerX
        var y = eventY - centerY
        val r = sqrt(x * x + y * y.toDouble())
        if (r > radius) {
            x *= radius / r.toFloat()
            y *= radius / r.toFloat()
        }
        currentPoint.x = x + centerX
        currentPoint.y = y + centerY
        colorPointer.setCurrentPoint(currentPoint)
    }

    private fun pickColor(color: Int) {
        colorListener?.onColorSelected(color, ColorUtil.formatColor(color))
    }

    fun setColorListener(listener: (Int, String) -> Unit) {
        this.colorListener = object : ColorListener {
            override fun onColorSelected(color: Int, colorHex: String) {
                listener(color, colorHex)
            }
        }
    }
}
