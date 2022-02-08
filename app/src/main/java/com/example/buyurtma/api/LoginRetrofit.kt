package com.example.buyurtma.api

import com.example.buyurtma.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object LoginRetrofit {

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    private val login by lazy {
        Retrofit.Builder()
            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val postRegister: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }

    val postLogin: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }

    val postKuryer: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }

    val getProfile: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }

    val getOrder: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }

    val getData: LoginApi by lazy {
        login.create(LoginApi::class.java)
    }
}
