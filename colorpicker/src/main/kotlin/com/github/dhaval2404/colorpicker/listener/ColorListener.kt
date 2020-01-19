package com.github.dhaval2404.colorpicker.listener

/**
 * Color Select Listener
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 23 Dec 2019
 */
interface ColorListener {

    /**
     *
     * Call when user select color
     *
     * @param color Color Resource
     * @param colorHex Hex String of Color Resource
     */
    fun onColorSelected(color: Int, colorHex: String)
}
