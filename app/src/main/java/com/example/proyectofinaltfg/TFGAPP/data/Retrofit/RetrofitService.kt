package com.example.proyectofinaltfg.TFGAPP.data.Retrofit

import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface RetrofitService {
    @GET("images/search")
    suspend fun listOfCats(): List<CatResponse>
}


object RetrofitServiceFactory {
    fun makeRetrofitService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
}
