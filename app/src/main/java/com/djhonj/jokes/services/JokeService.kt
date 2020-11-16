package com.djhonj.jokes.services

import retrofit2.http.GET
import com.djhonj.jokes.models.Joke
import retrofit2.Call

interface JokeService {
    @GET("random/jokes")
    fun getJokeRandom(): Call<Joke>
}