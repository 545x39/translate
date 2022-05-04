package com.example.translations.domain.repository

import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

interface DictionaryRepository {

    fun persistWord(word: String, language: Long, queried: Boolean = false): Single<Word>

    fun getDictionary(query: String? = null): Single<List<DictionaryEntry>>

    fun translate(query: Word, fromLanguage: Language, toLanguage: Language): Single<String>

    fun deleteEntry(entry: DictionaryEntry): Completable
}