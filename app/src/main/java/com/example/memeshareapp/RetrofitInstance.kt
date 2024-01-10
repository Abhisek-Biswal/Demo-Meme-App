package com.example.memeshareapp

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {

        Retrofit.Builder().baseUrl("https://meme-api.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    //connect retrofit to ApiInterface
    val apiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}