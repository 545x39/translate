package com.example.translations.framework.datasource.implementation.remote

import com.example.translations.framework.datasource.implementation.remote.Headers.ACCEPT
import com.example.translations.framework.datasource.implementation.remote.Headers.API_KEY_HEADER
import com.example.translations.framework.datasource.implementation.remote.Headers.CONTENT_TYPE
import com.example.translations.framework.datasource.implementation.remote.dto.LanguagesResponseDTOImpl
import com.example.translations.framework.datasource.implementation.remote.dto.TranslationRequestDTOImpl
import com.example.translations.framework.datasource.implementation.remote.dto.TranslationResponseDTOImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @POST(LANGUAGES)
    @Headers(
        CONTENT_TYPE,
        ACCEPT,
        API_KEY_HEADER
    )
    fun fetchLanguages(): Single<LanguagesResponseDTOImpl>

    @POST(TRANSLATE)
    @Headers(
        CONTENT_TYPE,
        ACCEPT,
        API_KEY_HEADER
    )
    fun fetchTranslation(@Body body: TranslationRequestDTOImpl): Single<TranslationResponseDTOImpl>
}