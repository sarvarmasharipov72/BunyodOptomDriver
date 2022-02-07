package com.example.buyurtma.ui.home.home_sub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Data(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val order: Order,
    val status: Int,
    val updatedAt: String,
    val user: String
): Parcelable