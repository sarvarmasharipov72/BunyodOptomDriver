package com.example.buyurtma.ui.login.model

data class Model(val phone: String, val password: String)
data class Phone(
    val type: String,
    val required: Boolean = true,
    val unique: Boolean = true,
    val trim: Boolean = true,
    val min: Int = 12
)
data class Password(val type: String, val required: Boolean = true)