package com.djhonj.jokes.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.djhonj.jokes.BuildConfig
import com.djhonj.jokes.R
import com.djhonj.jokes.interfaces.IJokePresenter
import com.djhonj.jokes.interfaces.IJokeView
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.models.Translate
import com.djhonj.jokes.models.Translator
import com.djhonj.jokes.prensenters.JokePresenter
import com.djhonj.jokes.services.JokeService
import com.djhonj.jokes.services.ServiceBuilder
import com.djhonj.jokes.services.TranslatorService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity(), IJokeView {
    private val presenter: IJokePresenter by lazy { JokePresenter(this) }
    private var jokeSaveInstance: List<Joke>? = null
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar.visibility = View.INVISIBLE

        val jokes = savedInstanceState?.getSerializable("jokeInstance")

        if (jokes == null) {
            //progressBar.visibility = View.VISIBLE
            //loadJokeRandom(type ?: "general")
            presenter.buscarChiste(type ?: "general")
        }

        buttonBuscar.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            presenter.buscarChiste(type ?: "general")
            //loadJokeRandom(type ?: "general")
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

        if (type != null) outState.putString("typeSelected", type)
    }

    //obtenemos el valor de la savedinsatance
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getSerializable("jokeInstance") != null) {
            val jokes: List<Joke>? = savedInstanceState.getSerializable("jokeInstance") as List<Joke>?
            jokes?.get(0)?.let {
                showChiste(it)
            }
        }

        if (savedInstanceState.getString("typeSelected") != null) {
            this.type = savedInstanceState.getString("typeSelected")
        }
    }

    //interactor
    private fun traducir(translate: Translate) {
        presenter.traducirTexto(translate)
    }

    //presenter
    fun onRadioChecked(view: View) {
        this.type = presenter.getTipoChiste(view.id)
    }

    override fun showChiste(responseJoke: Joke) {
        linear_layout_traduccion.visibility = View.INVISIBLE
        progressBar.visibility = View.INVISIBLE

        textViewSetup.text = String.format("- %s", responseJoke.setup)
        textViewPunchLine.text = String.format("- %s", responseJoke.punchline)
        tv_type.text = String.format("%s: %s", applicationContext.getString(R.string.tv_type), responseJoke.type)

        jokeSaveInstance = listOf(responseJoke)
    }

    override fun showTraduccion(chisteTraducido: Joke) {
        tv_setup_spanish.text = String.format("-%s", chisteTraducido.setup)
        tv_punchline_spanish.text = String.format("-%s", chisteTraducido.punchline)

        linear_layout_traduccion.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    override fun showError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}