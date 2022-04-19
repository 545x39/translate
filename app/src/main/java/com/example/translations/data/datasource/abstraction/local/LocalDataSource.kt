package com.example.translations.data.datasource.abstraction.local

import com.example.translations.data.datasource.abstraction.local.dto.LocalLanguageDTO
import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun getLanguage(id: Long): LanguageDTOImpl?

    fun getLanguages(): Single<List<LocalLanguageDTO>>

    fun persistLanguages(languages: List<Language>)

    fun getWord(id: Long): WordDTOImpl

    fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean = false
    ): Single<out WordDTO>

    fun persistTranslation(query: Word, translation: String, translationLanguage: Long)

    fun getTranslation(query: Word, toLanguage: Language): Single<out WordDTO>

    fun getQueriedWords(): Single<List<WordDTO>>

    fun getTranslations(word: WordDTO): List<WordDTO>

    fun deleteEntry(entry: DictionaryEntry)

}