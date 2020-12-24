package com.djhonj.jokes.interactors

import android.widget.Toast
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter

class JokeInteractor(presenter: IJokePresenter): IJokeInteractor {
    private val presenter: IJokePresenter by lazy { presenter }

    override fun buscar() {




        this.presenter.getChiste()
    }
}