package com.github.dhaval2404.colorpicker.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.github.dhaval2404.colorpicker.R
import com.github.dhaval2404.colorpicker.model.ColorShape

/**
 * Common method for {@see RecentColorAdapter and MaterialColorPickerAdapter}
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 28 March 2020
 */
object ColorViewBinding {

    /**
     *  Inflate ColorPicker Adapter Item View and return it
     *
     *  @param parent The ViewGroup into which the new View will be added after it is bound to
     *               an adapter position.
     *
     *  @return View A Adapter Item View
     */
    fun inflateAdapterItemView(parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        return inflater.inflate(
            R.layout.adapter_material_color_picker,
            parent,
            false
        )
    }

    /**
     * Set Card Background Color from provided hex color
     *
     * @param cardView CardView on which Background color will applied
     * @param hexColor String Color Hex. eg. #3399ff
     */
    fun setBackgroundColor(cardView: CardView, hexColor: String) {
        cardView.setCardBackgroundColor(Color.parseColor(hexColor))
    }

    /**
     * Set Card Corner Radius if provided shape is Square
     *
     * @param cardView CardView on which Corner Radius will applied
     * @param colorShape ColorShape Square or Round
     */
    fun setCardRadius(cardView: CardView, colorShape: ColorShape) {
        if (colorShape == ColorShape.SQAURE) {
            val resources = cardView.context.resources
            cardView.radius = resources.getDimension(R.dimen.color_card_square_radius)
        }
    }
}
