package com.djhonj.jokes.interceptors

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthenticationInterceptor(username: String, password: String): Interceptor {
    private var credentials: String = Credentials.basic(username, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        var original: Request = chain.request()
        var builder: Request.Builder = original.newBuilder()
            .header("Authorization", credentials)

        val request: Request = builder.build()

        return chain.proceed(request)
    }
}