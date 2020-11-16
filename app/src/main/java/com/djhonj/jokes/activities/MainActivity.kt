package com.djhonj.jokes.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.djhonj.jokes.R
import com.djhonj.jokes.models.Joke
import com.djhonj.jokes.services.JokeService
import com.djhonj.jokes.services.ServiceBuilder
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
            loadJokeRandom()
        }
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
                        textViewSetup.text = "- ${it.setup}"
                        textViewPunchLine.text = "- ${it.punchline}"
                        tv_type.text = "type: ${it.type}"
                    }
                }
            }
        })
    }
}