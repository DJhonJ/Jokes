package com.djhonj.jokes.prensenters

import android.os.Bundle
import com.djhonj.jokes.R
import com.djhonj.jokes.interactors.JokeInteractor
import com.djhonj.jokes.interactors.TranslatorInteractor
import com.djhonj.jokes.interfaces.IJokeInteractor
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.interfaces.IJokeView
import com.djhonj.jokes.interfaces.ITraslatorInteractor
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate

class JokePresenter(view: IJokeView): IJokePresenter {
    private val view: IJokeView by lazy { view }
    private val interactor: IJokeInteractor by lazy { JokeInteractor(this) }
    private val intetactorTranslator: ITraslatorInteractor by lazy { TranslatorInteractor(this) }

    override fun buscarChiste(type: String) {
        interactor.buscar(type)
    }

    override fun getChiste(joke: Joke) {
        this.view.showChiste(joke)
    }

    override fun getTipoChiste(id_check: Int): String {
        return when(id_check) {
            R.id.rb_programming -> "programming"
            R.id.rb_knock -> "knock-knock"
            else -> "general"
        }
    }

    override fun traducirTexto(translate: Translate) {
        intetactorTranslator.traducir(translate)
    }

    override fun getTraduccion(chisteTraducido: Joke) {
        view.showTraduccion(chisteTraducido)
    }

    override fun showError(mensaje: String) {
        this.view.showError(mensaje)
    }
}