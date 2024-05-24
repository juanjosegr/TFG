package com.example.proyectofinaltfg.TFGAPP.data.Retrofit

import com.example.proyectofinaltfg.TFGAPP.data.Model.CatResponse
import com.example.proyectofinaltfg.TFGAPP.data.Model.PharsesResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Interfaz que define las operaciones para obtener una lista de imágenes de gatos desde una API.
 */
interface RetrofitService {
    @GET("images/search")
    suspend fun listOfCats(): List<CatResponse>
}
/**
 * Interfaz que define las operaciones para obtener una lista de frases desde una API.
 */
interface PhrasesService {
    @GET("quotes?category=beauty")
    suspend fun listOfQuotes( @Header("X-Api-Key") apiKey: String): List<PharsesResponse>
}
/**
 * Objeto que se encarga de crear instancias de los servicios Retrofit.
 */
object RetrofitServiceFactory {
    private const val BASE_URL_CAT_API = "https://api.thecatapi.com/v1/"
    private const val BASE_URL_QUOTE_API = "https://api.api-ninjas.com/v1/"
    /**
     * Método para crear una instancia de RetrofitService para obtener imágenes de gatos.
     */
    fun makeCatService(): RetrofitService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_CAT_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RetrofitService::class.java)
    }
    /**
     * Método para crear una instancia de PhrasesService para obtener frases.
     */
    fun makePharsesService(): PhrasesService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_QUOTE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhrasesService::class.java)
    }

}

