package com.djhonj.jokes.interfaces

import com.djhonj.jokes.models.Joke

interface IJokeView {
    fun mostrarChiste(joke: Joke)
    fun mostrarTraduccion()
    fun guardarTipoChiste()
}