package com.djhonj.jokes.prensenters

import android.view.View
import com.djhonj.jokes.interactors.JokeInteractor
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.interfaces.IJokeView

class JokePresenter(view: IJokeView): IJokePresenter {
    private val view: IJokeView by lazy { view }
    private val interactor: IJokeInteractor by lazy { JokeInteractor(this) }

    override fun buscarChiste() {
        interactor.buscar()
    }

    override fun getChiste() {
        this.view.mostrarChiste()
    }
}