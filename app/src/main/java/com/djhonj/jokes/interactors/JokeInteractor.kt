package com.djhonj.jokes.interactors

import android.util.Log
import retrofit2.Call
import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.data.models.Joke
import com.djhonj.jokes.data.api.JokeService
import com.djhonj.jokes.data.api.ServiceBuilder
import retrofit2.Callback
import retrofit2.Response

class JokeInteractor(presenter: IJokePresenter): IJokeInteractor {
    private val presenter: IJokePresenter by lazy { presenter }

    override fun buscar(type: String) {
        val jokeService: JokeService = ServiceBuilder.buildService(BuildConfig.API_JOKES_URL, JokeService::class.java)
        val requestGet: Call<List<Joke>> = jokeService.getJokeRandomType(type)

        requestGet.enqueue(object: Callback<List<Joke>> {
            override fun onFailure(call: Call<List<Joke>>, t: Throwable) {
                Log.i("JokeInteractor", "error al buscar")
                presenter.showError("problemas buscando")
            }

            override fun onResponse(call: Call<List<Joke>>, response: Response<List<Joke>>) {
                if (response.isSuccessful) {
                    val joke: List<Joke>? = response.body()

                    joke?.let {
                        if (it.size > 0) presenter.getChiste(it.get(0))
                    }
                }
            }
        })
    }
}