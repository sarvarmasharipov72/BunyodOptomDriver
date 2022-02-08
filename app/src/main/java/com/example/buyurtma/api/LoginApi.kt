package com.example.buyurtma.api

import com.example.buyurtma.ui.buyurtmalar.BuyurtmaModel
import com.example.buyurtma.ui.home.home_sub.model.Orders
import com.example.buyurtma.ui.home.profile.model.Profile
import com.example.buyurtma.ui.login.model.Login
import com.example.buyurtma.ui.login.model.Model
import com.example.buyurtma.ui.login.model.Token
import retrofit2.Response
import retrofit2.http.*

interface LoginApi {

    @POST("/api/user/register")
    suspend fun postRegister (@Body login: Model)

    @POST("/api/user/login")
    suspend fun postLogin (@Body login: Login): Response<String>

    @POST("/api/user/kuryer")
    suspend fun postKuryer(@Body model: Model): Response<Token>

    @GET("/api/user/me")
    suspend fun getProfile(@Header("Authorization") auth: String): Response<Profile>

    @GET("/api/order-by/my")
    suspend fun getOrder(@Header("Authorization") auth: String): Response<Orders>

    @GET("/api/order/{id}")
    suspend fun getData(@Header("Authorization") auth: String, @Path("id") groupId: String): Response<BuyurtmaModel>
}