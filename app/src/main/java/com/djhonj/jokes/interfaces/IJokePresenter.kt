package com.djhonj.jokes.interfaces

import android.os.Bundle
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate

interface IJokePresenter {
    fun buscarChiste(type: String)
    fun getChiste(joke: Joke)
    fun getTipoChiste(id_check: Int): String
    fun showError(mensaje: String)
    fun traducirTexto(translate: Translate)
    fun getTraduccion(joke: Joke)
}