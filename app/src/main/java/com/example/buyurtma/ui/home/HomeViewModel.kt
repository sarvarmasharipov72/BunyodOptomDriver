package com.example.buyurtma.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.buyurtma.ui.home.home_sub.model.Orders
import com.example.buyurtma.ui.home.profile.model.Profile
import retrofit2.Response

class HomeViewModel : ViewModel() {

    var token = MutableLiveData<String>()

    var check = MutableLiveData<Int>()

    var itemData = MutableLiveData<Orders?>()

    var profileData = MutableLiveData<Profile>()

    var orders = MutableLiveData<Orders>()

    fun setCheck(check: Int) {
        this.check.value = check
    }

    fun setProfileData(profile: Profile) {
        profileData.value = profile
    }

    fun setOrders(orders: Orders) {
        this.orders.value = orders
    }

    fun setItemData(orders: Orders?) {
        itemData.value = orders
    }

    fun setToken(token: String) {
        this.token.value = token
    }
}