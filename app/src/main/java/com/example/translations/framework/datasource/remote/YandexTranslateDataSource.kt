package com.example.translations.framework.datasource.remote

import com.example.translations.data.source.remote.RemoteDataSource
import com.example.translations.data.source.remote.dto.LanguagesResponseDTO
import com.example.translations.data.source.remote.dto.TranslationResponseDTO
import com.example.translations.framework.datasource.remote.dto.TranslationRequestDtoImpl
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class YandexTranslateDataSource @Inject constructor(private val api: Api) : RemoteDataSource {

    override fun fetchLanguages(): Observable<out LanguagesResponseDTO> = api.fetchLanguages()

    override fun fetchTranslation(
        word: String,
        fromLanguage: String,
        toLanguage: String
    ): Observable<out TranslationResponseDTO> =
        api.fetchTranslation(TranslationRequestDtoImpl(fromLanguage, toLanguage, listOf(word)))
}