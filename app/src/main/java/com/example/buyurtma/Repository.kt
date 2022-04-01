package com.example.buyurtma

import com.example.buyurtma.api.LoginRetrofit
import com.example.buyurtma.ui.buyurtmalar.model.BuyurtmaModel
import com.example.buyurtma.ui.home.home_sub.model.Orders
import com.example.buyurtma.ui.home.profile.model.Profile
import com.example.buyurtma.ui.login.model.Login
import com.example.buyurtma.ui.login.model.Model
import com.example.buyurtma.ui.login.model.Token
import okhttp3.internal.EMPTY_RESPONSE
import retrofit2.Response
import java.net.SocketTimeoutException

class Repository() {


    suspend fun postRegister(login: Model) {
        LoginRetrofit.postRegister.postRegister(login)
    }

    suspend fun postLogin(login: Login): Response<String> {
        return LoginRetrofit.postLogin.postLogin(login)
    }

    suspend fun postKuryer(model: Model): Response<Token>{
        return LoginRetrofit.postKuryer.postKuryer(model)
    }

    suspend fun getProfile(role: String): Response<Profile> {
        return LoginRetrofit.getProfile.getProfile(role)
    }

    suspend fun getOrder(token: String): Response<Orders> {
        return LoginRetrofit.getOrder.getOrder(token)
    }

    suspend fun getData(token: String, id: String): Response<BuyurtmaModel> {
       return LoginRetrofit.getData.getData(token, id)
    }

//    suspend fun saveToken(token: String) {
//        dataStorProfile.saveToken(token)
//    }
//
//    suspend fun getToken(): String {
//        return dataStorProfile.getToken()
//    }
}