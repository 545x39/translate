package com.example.translations.data.source.remote

import com.example.translations.data.source.remote.dto.LanguagesResponseDTO
import com.example.translations.data.source.remote.dto.TranslationResponseDTO
import io.reactivex.rxjava3.core.Observable

interface RemoteDataSource {

    fun fetchLanguages(): Observable<out LanguagesResponseDTO>

    fun fetchTranslation(
        word: String,
        fromLanguage: String,
        toLanguage: String
    ): Observable<out TranslationResponseDTO>
}