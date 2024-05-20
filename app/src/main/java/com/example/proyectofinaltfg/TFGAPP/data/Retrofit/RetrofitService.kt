package com.example.proyectofinaltfg.TFGAPP.data.Retrofit

import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import com.example.proyectofinaltfg.TFGAPP.data.Model.PharsesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface RetrofitService {
    @GET("images/search")
    suspend fun listOfCats(): List<CatResponse>
}

interface PhrasesService {
    @GET("quotes?category=beauty")
    suspend fun listOfQuotes( @Header("X-Api-Key") apiKey: String): List<PharsesResponse>
}

object RetrofitServiceFactory {
    private const val BASE_URL_CAT_API = "https://api.thecatapi.com/v1/"
    private const val BASE_URL_QUOTE_API = "https://api.api-ninjas.com/v1/"

    fun makeCatService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CAT_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }

    fun makePharsesService(): PhrasesService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_QUOTE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhrasesService::class.java)
    }

}

