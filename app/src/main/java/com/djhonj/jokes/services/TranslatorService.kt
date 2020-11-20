package com.djhonj.jokes.services

import com.djhonj.jokes.models.Translate
import retrofit2.Call
//import com.djhonj.jokes.models.Translation
import com.djhonj.jokes.models.Translations
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface TranslatorService {
    @Headers("Content-Type: application/json; charset=utf8")
    @POST("v3/translate?version=2018-05-01")
    fun translator(@Body translate: Translate): Call<Translations>
}