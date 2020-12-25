package com.djhonj.jokes.interfaces

import com.djhonj.jokes.data.models.Joke
import com.djhonj.jokes.data.models.Translate

interface IJokePresenter {
    fun buscarChiste(type: String)
    fun getChiste(joke: Joke)
    fun getTipoChiste(id_check: Int): String
    fun showError(mensaje: String)
    fun traducirTexto(translate: Translate)
    fun getTraduccion(joke: Joke)
}