package com.example.translations.data.datasource.abstraction.remote

import com.example.translations.data.datasource.abstraction.remote.dto.LanguagesResponseDTO
import com.example.translations.data.datasource.abstraction.remote.dto.TranslationResponseDTO
import io.reactivex.rxjava3.core.Single

interface RemoteDataSource {

    fun fetchLanguages(): Single<LanguagesResponseDTO>

    fun fetchTranslation(
        word: String,
        fromLanguage: String,
        toLanguage: String
    ): Single<TranslationResponseDTO>
}