package com.djhonj.jokes.interfaces

import com.djhonj.jokes.data.models.Translate

interface ITraslatorInteractor {
    fun traducir(translate: Translate)
}