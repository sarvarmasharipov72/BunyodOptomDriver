package com.example.buyurtma.ui.home.home_sub.model

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


@Parcelize
data class Orders(
    val `data`: List<Data>,
    val success: Boolean
): Parcelable