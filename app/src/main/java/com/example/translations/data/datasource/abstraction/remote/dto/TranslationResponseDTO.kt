package com.example.translations.data.datasource.abstraction.remote.dto

interface TranslationResponseDTO {
    val translations: List<TranslationDTO>
}

interface TranslationDTO {
    val text: String
    val detectedLanguageCode: String
}