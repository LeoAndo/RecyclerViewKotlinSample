package com.template.simplerecyclerviewkotlin

import androidx.annotation.DrawableRes

data class OrderItem(@DrawableRes val imageResource: Int, val title: String, val price: Int, val amount: Int)