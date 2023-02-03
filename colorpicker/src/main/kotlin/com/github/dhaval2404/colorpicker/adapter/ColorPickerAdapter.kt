package com.github.dhaval2404.colorpicker.adapter

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.dhaval2404.colorpicker.listener.ColorListener
import com.github.dhaval2404.colorpicker.model.ColorShape
import com.github.dhaval2404.colorpicker.util.SharedPref
import androidx.cardview.widget.CardView

/**
 * Adapter for Recent Color
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
class ColorPickerAdapter(private val colors: List<String>) :
    RecyclerView.Adapter<ColorPickerAdapter.ColorViewHolder>() {

    private var colorShape = ColorShape.CIRCLE
    private var colorListener: ColorListener? = null
    private var emptyColor = "#E0E0E0"

    fun setColorListener(listener: ColorListener) {
        this.colorListener = listener
    }

    fun setEmptyColor(color: String) {
        this.emptyColor = color
    }

    fun setColorShape(colorShape: ColorShape) {
        this.colorShape = colorShape
    }

    override fun getItemCount() = SharedPref.RECENT_COLORS_LIMIT

    fun getItem(position: Int): String {
        return if (position < colors.size) {
            colors[position]
        } else {
            emptyColor
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val rootView = ColorViewBinding.inflateAdapterItemView(parent)
        return ColorViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class ColorViewHolder(private val rootView: View) :
        RecyclerView.ViewHolder(rootView) {

        private val cardView = rootView as CardView

        init {
            cardView.setOnClickListener {
                val index = it.tag as Int
                if (index < colors.size) {
                    val color = getItem(index)
                    colorListener?.onColorSelected(Color.parseColor(color), color)
                }
            }
        }

        fun bind(position: Int) {
            val color = getItem(position)

            cardView.tag = position
            ColorViewBinding.setBackgroundColor(cardView, color)
            ColorViewBinding.setCardRadius(cardView, colorShape)
        }
    }
}
