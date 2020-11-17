package com.djhonj.jokes.interceptors

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class ChangeableBaseUrlInterceptor(oldUrl: String, newUrl: String) : Interceptor {
    private val oldUrl: String = oldUrl
    private val newUrl: String = newUrl

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        //val newUrl:String = request.url.toString().replace(request.url.host, this.url)
        //return chain.proceed(request.newBuilder().url(request.url.toString().replace("https://us-central1-dadsofunny.cloudfunctions.net/DadJokes/", url)).build())
        return chain.proceed(request.newBuilder().url(request.url.toString().replace(oldUrl, newUrl)).build())
    }
}