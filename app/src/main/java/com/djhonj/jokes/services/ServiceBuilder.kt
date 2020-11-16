package com.djhonj.jokes.services

import com.djhonj.jokes.models.Joke
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    // lo podemos usar para declarar funciones y que se puedan acceder de amnera static
    //companion object Message {
    private val API_BASE_URL = "https://us-central1-dadsofunny.cloudfunctions.net/DadJokes/"

    //instancia para el interteptor de peticiones y respuestas
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())

    private val retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        return retrofit.create(serviceType)
    }
    //init es para inicializar variables, no es un constructor
//    init {
//
//    }
}