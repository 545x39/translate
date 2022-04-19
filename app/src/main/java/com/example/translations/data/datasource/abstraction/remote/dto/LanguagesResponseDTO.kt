package com.example.translations.data.datasource.abstraction.remote.dto

interface LanguagesResponseDTO {

    val languages: List<RemoteLanguageDTO>
}

interface RemoteLanguageDTO {
    val code: String
    val name: String?
}