package com.bangkit.jajananapp_bintang.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.bangkit.jajananapp_bintang.R

data class Category(
    @DrawableRes val imageCategory: Int,
    @StringRes val textCategory: Int
)

val dummyCategory = listOf(
    R.drawable.ruau to R.string.riau,
    R.drawable.aceh to R.string.aceh,
    R.drawable.sumut to R.string.sumut,
    R.drawable.sumbar to R.string.sumbar,
    R.drawable.sumsel to R.string.sumsel,
    R.drawable.jabar to R.string.jabar,
    R.drawable.jateng to R.string.jateng,
    R.drawable.jatim to R.string.jatim,
    R.drawable.kalbar to R.string.kalbar,
    R.drawable.sulut to R.string.sulut,
    R.drawable.papua to R.string.papua,
).map { Category(it.first, it.second) }