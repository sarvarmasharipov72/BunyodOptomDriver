package com.example.buyurtma.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buyurtma.ui.home.home_sub.model.Orders

class HomeViewModel : ViewModel() {

    var token = MutableLiveData<String>()

    var itemData = MutableLiveData<Orders?>()

    fun setItemData(orders: Orders?) {
        itemData.value = orders
    }

    fun setToken(token: String) {
        this.token.value = token
    }
}