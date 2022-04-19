package com.example.translations.data.datasource.abstraction.remote.dto

interface TranslationRequestDTO {
    val fromLanguage: String
    val toLanguage: String
    val texts: List<String>
}