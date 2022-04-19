package com.example.translations.framework.datasource.implementation.local

import com.example.translations.data.datasource.abstraction.local.LocalDataSource
import com.example.translations.data.datasource.abstraction.local.dto.LocalLanguageDTO
import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.framework.datasource.implementation.local.database.Database
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.TranslationDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers.io
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class RoomDataSource @Inject constructor(private val database: Database) : LocalDataSource {

    override fun getLanguage(id: Long) = database.languageDAO().getLanguage(id)

    override fun getLanguages(): Single<List<LocalLanguageDTO>> =
        database.languageDAO().getLanguages() as Single<List<LocalLanguageDTO>>

    override fun persistLanguages(languages: List<Language>) {
        val list = mutableListOf<LanguageDTOImpl>().apply {
            languages.map { add(LanguageDTOImpl(it.code, it.name)) }
        }
        database.languageDAO().insert(list)
    }

    override fun getWord(id: Long) = database.dictionaryDAO().getWord(id)

    override fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean
    ): Single<out WordDTO> =
        database.dictionaryDAO().insertWord(WordDTOImpl(value, languageId, queried))
            .andThen(database.dictionaryDAO().getWord(value, languageId))

    override fun persistTranslation(
        query: Word,
        translation: String,
        translationLanguage: Long
    ) {
        saveAndGetWord(translation, translationLanguage)
            .subscribeOn(io())
            .subscribe { translation ->
                database.dictionaryDAO().insertTranslation(
                    TranslationDTOImpl(
                        query.id,
                        translation.id,
                        query.language,
                        translationLanguage
                    )
                )
            }
    }


    override fun getTranslation(word: Word, toLanguage: Language): Single<out WordDTO> =
        database.dictionaryDAO().getTranslation(word.id, word.language, toLanguage.id)

    override fun getQueriedWords(): Single<List<WordDTO>> =
        database.dictionaryDAO().getDictionary() as Single<List<WordDTO>>

    override fun getTranslations(word: WordDTO): List<WordDTO> =
        database.dictionaryDAO().getTranslations(word.id)

    override fun deleteEntry(entry: DictionaryEntry) {
            database.dictionaryDAO().deleteEntry(entry)
    }

}