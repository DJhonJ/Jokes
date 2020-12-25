package com.djhonj.jokes.data.api

import retrofit2.http.GET
import com.djhonj.jokes.data.models.Joke
import retrofit2.Call
import retrofit2.http.Path

interface JokeService {
    @GET("random/jokes") //type/programming
    fun getJokeRandom(): Call<Joke>

    @GET("random/type/{type}")
    fun getJokeRandomType(@Path("type") type: String): Call<List<Joke>>
}