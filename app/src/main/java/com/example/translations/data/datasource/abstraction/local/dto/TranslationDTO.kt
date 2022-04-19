package com.example.translations.data.datasource.abstraction.local.dto

interface TranslationDTO {
    val wordId: Long
    val translationId: Long
    val fromLanguage: Long
    val toLanguage: Long
    var id: Int
}