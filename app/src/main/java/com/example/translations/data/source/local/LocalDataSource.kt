package com.example.translations.data.source.local

import com.example.translations.data.source.local.dto.LocalLanguageDTO
import com.example.translations.data.source.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.framework.datasource.local.dto.WordDtoImpl
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface LocalDataSource {

    fun getLanguages(): Observable<out LocalLanguageDTO>

    fun persistLanguages(languages: List<Language>)

    fun getWord(id: Long): WordDtoImpl

    fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean = false
    ): Single<out WordDTO>

    fun persistTranslation(query: Word, translation: String, translationLanguage: Long)

    fun getTranslation(query: Word, toLanguage: Language): Observable<out WordDTO>

    fun getQueriedWords(): Observable<out WordDTO>

    fun getTranslations(word: WordDTO): List<WordDTO>

    fun deleteEntry(entry: DictionaryEntry)

}