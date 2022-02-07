package com.example.buyurtma.ui.home.home_sub.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val __v: Int,
    val _id: String,
    val address: String,
    val amount: Int,
    val count: Int,
    val createdAt: String,
    val isPayed: Boolean,
    val order: Int,
    val product: List<Product>,
    val status: Int,
    val updatedAt: String,
    val user: String
): Parcelable