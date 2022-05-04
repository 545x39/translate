package com.example.translations.data.source.remote.dto

interface LanguagesResponseDTO {

    val languages: List<RemoteLanguageDTO>
}

interface RemoteLanguageDTO {
    val code: String
    val name: String?
}