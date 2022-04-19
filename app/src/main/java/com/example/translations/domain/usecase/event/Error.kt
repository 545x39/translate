package com.example.translations.domain.usecase.event

sealed class Error {
    object Unknown : Error()
    object CouldNotLoadLanguages : Error()
    object TranslationNotFound : Error()
    object SourceLanguageNotFound : Error()
}