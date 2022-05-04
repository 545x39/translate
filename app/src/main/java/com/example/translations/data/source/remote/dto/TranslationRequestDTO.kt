package com.example.translations.data.source.remote.dto

interface TranslationRequestDTO {
    val fromLanguage: String
    val toLanguage: String
    val texts: List<String>
}