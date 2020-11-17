package com.djhonj.jokes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.R
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate
import com.djhonj.jokes.models.Translation
import com.djhonj.jokes.models.Translations
import com.djhonj.jokes.services.JokeService
import com.djhonj.jokes.services.ServiceBuilder
import com.djhonj.jokes.services.TranslatorService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadJokeRandom()

        buttonBuscar.setOnClickListener {
            Toast.makeText(this, "Start", Toast.LENGTH_SHORT).show()
            loadJokeRandom()
            Toast.makeText(this, "End", Toast.LENGTH_SHORT).show()
        }

        //traduccir(Translate(listOf("Hola", "Chao"), "en-es"))
    }

    private fun loadJokeRandom() {
        val jokeService: JokeService = ServiceBuilder.buildService(JokeService::class.java)
        val requestGet: Call<Joke> = jokeService.getJokeRandom()

        requestGet.enqueue(object: Callback<Joke> {
            override fun onFailure(call: Call<Joke>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problemas en la peticion", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Joke>, response: Response<Joke>) {
                if (response.isSuccessful) {
                    val joke: Joke? = response.body()
                    joke?.let {
                        //textViewSetup.text = "- ${it.setup}"
                        //textViewPunchLine.text = "- ${it.punchline}"
                        tv_type.text = "type: ${it.type}"

                        traduccir(Translate(listOf(it.setup.toString(), it.punchline.toString()), "en-es"))
                        //textViewSetup.text = "- ${jokeSpanish?.setup}"
                        //textViewPunchLine.text = "- ${jokeSpanish?.punchline}"
                    }
                }
            }
        })
    }

    private fun traduccir(translate: Translate): Joke? {
        val serviceBuilder = ServiceBuilder.changeUrlBase(BuildConfig.API_JOKES_URL, BuildConfig.API_TRANSLATOR_URL)

        val translatorService: TranslatorService = serviceBuilder.buildService(TranslatorService::class.java, BuildConfig.API_TRANSLATOR_USERNAME, BuildConfig.API_TRANSLATOR_PASSWORD)
        val request: Call<Translations> = translatorService.translator(translate)
        val joke: Joke? = null

        request.enqueue(object: Callback<Translations> {
            override fun onFailure(call: Call<Translations>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problemas al traducir", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Translations>, response: Response<Translations>) {
                if (response.isSuccessful) {
                    val translation: Translations? = response.body()
                    translation?.let {
                        var uno = it.translations.get(0).get("translation").toString()
                        var dos = it.translations.get(1).get("translation").toString()

                        joke?.setup = uno
                        joke?.punchline = dos

                        textViewSetup.text = "- ${uno}"
                        textViewPunchLine.text = "- ${dos}"
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Problemas al traducir", Toast.LENGTH_SHORT).show()
                }
            }
        })

        return joke
    }
}