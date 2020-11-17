package com.djhonj.jokes.models

data class Translations(var translations: List<Map<String, String>>, var word_count: Int, var character_count: Int)

data class Translation(var translation: String)