package com.template.simplerecyclerviewkotlin

import androidx.annotation.DrawableRes

class OrderItem(@DrawableRes val imageResource: Int, val title: String, val price: Int, val amount: Int)