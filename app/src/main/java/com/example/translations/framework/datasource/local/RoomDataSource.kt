package com.example.translations.framework.datasource.local

import com.example.translations.data.source.local.LocalDataSource
import com.example.translations.data.source.local.dto.LocalLanguageDTO
import com.example.translations.data.source.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.framework.datasource.local.database.Database
import com.example.translations.framework.datasource.local.dto.LanguageDtoImpl
import com.example.translations.framework.datasource.local.dto.TranslationDtoImpl
import com.example.translations.framework.datasource.local.dto.WordDtoImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class RoomDataSource @Inject constructor(private val database: Database) : LocalDataSource {

    override fun getLanguages(): Observable<out LocalLanguageDTO> =
        database.languageDAO().getLanguages()

    override fun persistLanguages(languages: List<Language>) {
        languages.map { LanguageDtoImpl(it.code, it.name) }
            .also { database.languageDAO().insert(it) }
    }

    override fun getWord(id: Long) = database.dictionaryDAO().getWord(id)

    override fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean
    ): Single<out WordDTO> =
        database.dictionaryDAO().insertWord(WordDtoImpl(value, languageId, queried))
            .andThen(database.dictionaryDAO().getWord(value, languageId))

    override fun persistTranslation(
        query: Word,
        translation: String,
        translationLanguage: Long
    ) {
        saveAndGetWord(translation, translationLanguage)
            .subscribeOn(Schedulers.io())
            .subscribe { tr ->
                database.dictionaryDAO().insertTranslation(
                    TranslationDtoImpl(
                        query.id,
                        tr.id,
                        query.language,
                        translationLanguage
                    )
                )
            }
    }

    override fun getTranslation(query: Word, toLanguage: Language): Observable<out WordDTO> =
        database.dictionaryDAO().getTranslation(query.id, query.language, toLanguage.id)

    override fun getQueriedWords(): Observable<out WordDTO> = database.dictionaryDAO().getDictionary()

    override fun getTranslations(word: WordDTO): List<WordDTO> =
        database.dictionaryDAO().getTranslations(word.id)

    override fun deleteEntry(entry: DictionaryEntry) {
        database.dictionaryDAO().deleteEntry(entry)
    }

}