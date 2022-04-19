package com.example.translations.framework.datasource.implementation.remote

import com.example.translations.data.datasource.abstraction.remote.RemoteDataSource
import com.example.translations.data.datasource.abstraction.remote.dto.LanguagesResponseDTO
import com.example.translations.data.datasource.abstraction.remote.dto.TranslationResponseDTO
import com.example.translations.framework.datasource.implementation.remote.dto.TranslationRequestDTOImpl
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class YandexTranslateDataSource @Inject constructor(private val api: Api) : RemoteDataSource {

    override fun fetchLanguages(): Single<LanguagesResponseDTO> = api.fetchLanguages() as Single<LanguagesResponseDTO>

    override fun fetchTranslation(
        word: String,
        fromLanguage: String,
        toLanguage: String
    ): Single<TranslationResponseDTO> =
        api.fetchTranslation(TranslationRequestDTOImpl(fromLanguage, toLanguage, listOf(word))) as Single<TranslationResponseDTO>
}