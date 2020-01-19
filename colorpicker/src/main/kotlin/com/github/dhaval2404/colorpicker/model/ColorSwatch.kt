package com.github.dhaval2404.colorpicker.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Used for Color Palette Selection
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 24 Dec 2019
 */
@Parcelize
enum class ColorSwatch(val value: String) : Parcelable {
    _50("50"),
    _100("100"),
    _200("200"),
    _300("300"),
    _400("400"),
    _500("500"),
    _600("600"),
    _700("700"),
    _800("800"),
    _900("900"),
    A100("a100"),
    A200("a200"),
    A300("a300"),
    A400("a400");
}
