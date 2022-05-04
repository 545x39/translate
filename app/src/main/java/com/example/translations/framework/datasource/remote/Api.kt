package com.example.translations.framework.datasource.remote

import com.example.translations.BuildConfig
import com.example.translations.framework.datasource.remote.dto.LanguagesResponseDtoImpl
import com.example.translations.framework.datasource.remote.dto.TranslationRequestDtoImpl
import com.example.translations.framework.datasource.remote.dto.TranslationResponseDtoImpl
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Api {

    @POST("languages")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Authorization: Api-Key ${BuildConfig.API_KEY}"
    )
    fun fetchLanguages(): Observable<LanguagesResponseDtoImpl>

    @POST("translate")
    @Headers(
        "Content-Type: Application/Raw",
        "Accept: application/json;charset=utf-8",
        "Authorization: Api-Key ${BuildConfig.API_KEY}"
    )
    fun fetchTranslation(@Body body: TranslationRequestDtoImpl): Observable<TranslationResponseDtoImpl>
}