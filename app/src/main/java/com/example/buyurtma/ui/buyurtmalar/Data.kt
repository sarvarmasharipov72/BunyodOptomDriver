package com.example.buyurtma.ui.buyurtmalar

data class Data(
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
    val user: User
)