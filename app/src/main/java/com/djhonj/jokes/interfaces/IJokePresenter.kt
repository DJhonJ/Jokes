package com.djhonj.jokes.interfaces

import com.djhonj.jokes.models.Joke

interface IJokePresenter {
    fun buscarChiste(type: String)
    fun getChiste(joke: Joke)
}