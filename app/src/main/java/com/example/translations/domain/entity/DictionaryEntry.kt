package com.example.translations.domain.entity

data class DictionaryEntry(
    val word: Word,
    val translations: List<Word> = listOf()
)
