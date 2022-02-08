package com.example.buyurtma.ui.login.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.buyurtma.Repository
import com.example.buyurtma.ui.home.home_sub.model.Orders
import com.example.buyurtma.ui.home.profile.model.Profile
import com.example.buyurtma.ui.login.model.Model
import com.example.buyurtma.ui.login.model.Token
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginViewModel(private val repository: Repository): ViewModel() {

    var token = MutableLiveData<Response<Token>>()
    var profileData = MutableLiveData<Response<Profile>>()
    var orders = MutableLiveData<Response<Orders>>()


    fun postRegister(login: Model) {
        viewModelScope.launch {
        repository.postRegister(login)
        }
    }


    fun postKuryer(model: Model) {
        viewModelScope.launch {
            token.value = repository.postKuryer(model)
        }
    }

    fun getProfile(role: String) {
        viewModelScope.launch {
            profileData.value = repository.getProfile(role)
        }
    }

    fun getOrder(token: String) {
        viewModelScope.launch {
            orders.value = repository.getOrder(token)
        }
    }
}