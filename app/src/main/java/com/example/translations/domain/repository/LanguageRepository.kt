package com.example.translations.domain.repository

import com.example.translations.domain.entity.Language
import io.reactivex.rxjava3.core.Single

interface LanguageRepository {

    fun getLanguages(): Single<List<Language>>
}