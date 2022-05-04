package com.example.translations.data.source.remote.dto

interface TranslationResponseDTO {
    val translations: List<TranslationDto>
}

interface TranslationDto {
    val text: String
    val detectedLanguageCode: String
}