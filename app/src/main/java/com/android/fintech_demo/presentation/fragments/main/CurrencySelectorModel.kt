package com.android.fintech_demo.presentation.fragments.main

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import com.android.fintech_demo.R
import com.android.fintech_demo.presentation.utils.Currency

data class CurrencySelectorModel(
    val currency: Currency,
    val currencyName: String,
    val currencySign: String,
) {
    var isSelected: Boolean = false
}

@DrawableRes
fun CurrencySelectorModel.getBackground(): Int {
    return if (isSelected) R.drawable.shape_background_currency_selected else R.drawable.shape_background_currency_unselected
}

@ColorRes
fun CurrencySelectorModel.textColor(): Int {
    return if (isSelected) R.color.main_white else R.color.main_grey
}