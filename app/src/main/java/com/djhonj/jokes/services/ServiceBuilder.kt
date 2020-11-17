package com.djhonj.jokes.services

import android.text.TextUtils
import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.interceptors.BasicAuthenticationInterceptor
import com.djhonj.jokes.interceptors.ChangeableBaseUrlInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    // lo podemos usar para declarar funciones y que se puedan acceder de amnera static
    //companion object Message {
    //private val API_BASE_URL = ""

    //instancia para el interteptor de peticiones y respuestas
    val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(BuildConfig.API_JOKES_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())

    private var retrofit: Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T {
        val okHttpClient2: OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

         val builder2: Retrofit.Builder = Retrofit.Builder().baseUrl(BuildConfig.API_JOKES_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient2.build())

        var retrofit2: Retrofit = builder2.build()

        return retrofit2.create(serviceType)
    }

    fun <T> buildService(serviceType: Class<T>, username: String, password: String):T {
        if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
            okHttpClient.addInterceptor(BasicAuthenticationInterceptor(username, password))
            builder.client(okHttpClient.build())
            retrofit = builder.build()
        }

        return retrofit.create(serviceType)
    }

    fun changeUrlBase(oldUrl: String, newUrl: String): ServiceBuilder {
        if (!TextUtils.isEmpty(oldUrl)) {
            okHttpClient.addInterceptor(ChangeableBaseUrlInterceptor(oldUrl, newUrl))
            builder.client(okHttpClient.build())
            retrofit = builder.build()
        }

        return ServiceBuilder
    }

//    fun changeBaseUrl(url: String) {
//        API_BASE_URL = url
//    }
    //init es para inicializar variables, no es un constructor
//    init {
//
//    }
}