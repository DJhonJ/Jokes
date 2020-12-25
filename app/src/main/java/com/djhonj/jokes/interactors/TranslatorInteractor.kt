package com.djhonj.jokes.interactors

import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.interfaces.ITraslatorInteractor
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate
import com.djhonj.jokes.models.Translator
import com.djhonj.jokes.services.ServiceBuilder
import com.djhonj.jokes.services.TranslatorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TranslatorInteractor(presenter: IJokePresenter): ITraslatorInteractor {
    private val presenter: IJokePresenter by lazy { presenter }

    override fun traducir(translate: Translate) {
        val translatorService: TranslatorService = ServiceBuilder.buildService(BuildConfig.API_TRANSLATOR_URL,
            TranslatorService::class.java, BuildConfig.API_TRANSLATOR_USERNAME, BuildConfig.API_TRANSLATOR_PASSWORD)
        val request: Call<Translator> = translatorService.translator(translate)

        request.enqueue(object: Callback<Translator> {
            override fun onFailure(call: Call<Translator>, t: Throwable) {
                presenter.showError("Problemas al traducir")
            }

            override fun onResponse(call: Call<Translator>, response: Response<Translator>) {
                if (response.isSuccessful) {
                    val translation: Translator? = response.body()
                    translation?.let {
                        val setup = it.translations.get(0).get("translation").toString()
                        val punch = it.translations.get(1).get("translation").toString()

                        presenter.getTraduccion(Joke(setup=setup, punchline=punch))
                    }
                } else {
                    presenter.showError("Problemas al traducir")
                }
            }
        })
    }
}