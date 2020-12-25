package com.djhonj.jokes.services

import android.text.TextUtils
import com.djhonj.jokes.interceptors.BasicAuthenticationInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception

object ServiceBuilder {
    //Este objeto static, es un generador de servicios para nuestras peticiones
    // lo podemos usar para declarar funciones y que se puedan acceder de manera static
    //companion object Message {
    //private val API_BASE_URL = ""

    private lateinit var retrofit: Retrofit
    //instancia para el interteptor de peticiones y respuestas
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private fun createBuilder(baseUrl: String, interceptor: Interceptor? = null): Retrofit.Builder {
        if (interceptor != null) okHttpClient.addInterceptor(interceptor)

        val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
        return builder
    }

    fun <T> buildService(baseUrl: String, serviceType: Class<T>, username: String, password: String): T {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            retrofit = createBuilder(baseUrl, interceptor = BasicAuthenticationInterceptor(username, password)).build()
        } else {
          throw Exception("username or password null")
        }

        return retrofit.create(serviceType)
    }

    fun <T> buildService(baseUrl: String, serviceType: Class<T>): T {
        retrofit = createBuilder(baseUrl).build() ?: throw Exception("error en build service")
        return retrofit.create(serviceType)
    }
}