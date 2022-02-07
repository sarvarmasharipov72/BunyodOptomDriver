package com.example.buyurtma.ui.home.home_sub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val _id: String,
    val amount: Int,
    val count: Int,
    val product: String
): Parcelable