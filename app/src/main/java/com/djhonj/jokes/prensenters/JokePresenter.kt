package com.djhonj.jokes.prensenters

import android.view.View
import com.djhonj.jokes.interactors.JokeInteractor
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.interfaces.IJokeView
import com.djhonj.jokes.models.Joke

class JokePresenter(view: IJokeView): IJokePresenter {
    private val view: IJokeView by lazy { view }
    private val interactor: IJokeInteractor by lazy { JokeInteractor(this) }

    override fun buscarChiste(type: String) {
        interactor.buscar(type)
    }

    override fun getChiste(joke: Joke) {
        this.view.mostrarChiste(joke)
    }
}