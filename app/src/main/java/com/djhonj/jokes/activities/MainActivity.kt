package com.djhonj.jokes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.R
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate
import com.djhonj.jokes.models.Translations
import com.djhonj.jokes.services.JokeService
import com.djhonj.jokes.services.ServiceBuilder
import com.djhonj.jokes.services.TranslatorService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity() {
    private var jokeSaveInstance: List<Joke>? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val jokes = savedInstanceState?.getSerializable("jokeInstance")

        if (jokes == null) {
            progressBar.visibility = View.VISIBLE
            loadJokeRandom(type ?: "general")
        }

        buttonBuscar.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            loadJokeRandom(type ?: "general")
        }

        buttonTraducir.setOnClickListener {
            val setup: String = textViewSetup.text.toString().split("-")[1]
            val punchline: String = textViewPunchLine.text.toString().split("-")[1]

            progressBar.visibility = View.VISIBLE

            traducir(Translate(listOf(setup,  punchline), "en-es"))
        }
    }

    //guardamos el valor en la saveinstance
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        if (jokeSaveInstance != null) {
            outState.putSerializable("jokeInstance", jokeSaveInstance as Serializable)
        }

        if (type != null) {
           outState.putString("typeSelected", type)
        }
    }

    //obtenemos el valor
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getSerializable("jokeInstance") != null) {
            val jokes: List<Joke>? = savedInstanceState.getSerializable("jokeInstance") as List<Joke>?
            jokes?.get(0)?.let {
                textViewSetup.text = String.format("- %s", it.setup)
                textViewPunchLine.text = String.format("- %s", it.punchline)
                tv_type.text = String.format("%s: %s", getString(R.string.tv_type), it.type)

                jokeSaveInstance = listOf(it)
            }
        }

        if (savedInstanceState.getString("typeSelected") != null) {
            this.type = savedInstanceState.getString("typeSelected")
        }
    }

    private fun loadJokeRandom(type: String) {
        val jokeService: JokeService = ServiceBuilder.buildService(JokeService::class.java)
        val requestGet: Call<List<Joke>> = jokeService.getJokeRandomType(type)

        requestGet.enqueue(object: Callback<List<Joke>> {
            override fun onFailure(call: Call<List<Joke>>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problemas en la peticion onFailure3", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Joke>>, response: Response<List<Joke>>) {
                if (response.isSuccessful) {
                    linear_layout_traduccion.visibility = View.INVISIBLE

                    val joke: List<Joke>? = response.body()
                    joke?.let {
                        progressBar.visibility = View.INVISIBLE

                        if (it.size > 0) {
                            textViewSetup.text = String.format("- %s", it.get(0).setup)
                            textViewPunchLine.text = String.format("- %s", it.get(0).punchline)
                            tv_type.text = String.format("%s: %s", applicationContext.getString(R.string.tv_type), it.get(0).type)

                            jokeSaveInstance = listOf(it.get(0))
                        }
                    }
                }
            }
        })
    }

    private fun traducir(translate: Translate) {
        val serviceBuilder = ServiceBuilder.changeUrlBase(BuildConfig.API_JOKES_URL, BuildConfig.API_TRANSLATOR_URL)
        val translatorService: TranslatorService = serviceBuilder.buildService(TranslatorService::class.java,
                                        BuildConfig.API_TRANSLATOR_USERNAME, BuildConfig.API_TRANSLATOR_PASSWORD)
        val request: Call<Translations> = translatorService.translator(translate)
        //val joke: Joke? = null

        request.enqueue(object: Callback<Translations> {
            override fun onFailure(call: Call<Translations>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Problemas al traducir", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Translations>, response: Response<Translations>) {
                if (response.isSuccessful) {
                    val translation: Translations? = response.body()
                    translation?.let {
                        val setup = it.translations.get(0).get("translation").toString()
                        val punch = it.translations.get(1).get("translation").toString()

                        //joke?.setup = setup
                        //joke?.punchline = punch

                        tv_setup_spanish.text = String.format("-%s", setup)
                        tv_punchline_spanish.text = String.format("-%s", punch)

                        linear_layout_traduccion.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Problemas al traducir", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun onRadioChecked(view: View) {
        this.type = when(view.id) {
            //R.id.rb_generic -> "generic"
            R.id.rb_programming -> "programming"
            R.id.rb_knock -> "knock-knock"
            else -> "general"
        }
    }
}
