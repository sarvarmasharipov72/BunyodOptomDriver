package com.example.buyurtma.ui.login.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Login(val phone: String, val password: String, val error: String = "") : Parcelable