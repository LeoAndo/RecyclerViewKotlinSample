package com.template.simplerecyclerviewkotlin

import androidx.annotation.DrawableRes

data class Item(
    @DrawableRes val imageResource: Int,
    var title: String,
    val price: Int,
    val amount: Int
)