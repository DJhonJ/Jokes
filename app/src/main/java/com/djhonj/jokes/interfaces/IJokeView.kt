package com.djhonj.jokes.interfaces

import com.djhonj.jokes.data.models.Joke

interface IJokeView {
    fun showChiste(joke: Joke)
    fun showTraduccion(joke: Joke)
    fun showError(mensaje: String)
}