package com.djhonj.jokes.models

import java.io.Serializable

data class Joke(var id: Int? = null, var type: String? = null, var setup: String? = null, var punchline: String? = null) : Serializable