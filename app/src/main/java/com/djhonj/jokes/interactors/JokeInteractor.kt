package com.djhonj.jokes.interactors

import android.util.Log
import android.view.View
import retrofit2.Call
import android.widget.Toast
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.services.JokeService
import com.djhonj.jokes.services.ServiceBuilder
import retrofit2.Callback
import retrofit2.Response

class JokeInteractor(presenter: IJokePresenter): IJokeInteractor {
    private val _presenter: IJokePresenter by lazy { presenter }

    override fun buscar(type: String) {
        val jokeService: JokeService = ServiceBuilder.buildService(JokeService::class.java)
        val requestGet: Call<List<Joke>> = jokeService.getJokeRandomType(type)

        requestGet.enqueue(object: Callback<List<Joke>> {
            override fun onFailure(call: Call<List<Joke>>, t: Throwable) {
                Log.i("JokeInteractor", "error al buscar")
            }

            override fun onResponse(call: Call<List<Joke>>, response: Response<List<Joke>>) {
                if (response.isSuccessful) {
                    val joke: List<Joke>? = response.body()

                    joke?.let {
                        if (it.size > 0) _presenter.getChiste(it.get(0))
                    }
                }
            }
        })
    }
}