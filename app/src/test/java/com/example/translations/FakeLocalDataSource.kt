package com.example.translations

import com.example.translations.data.datasource.abstraction.local.LocalDataSource
import com.example.translations.data.datasource.abstraction.local.dto.LocalLanguageDTO
import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.TranslationDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import com.example.translations.framework.datasource.implementation.remote.dto.LanguagesResponseDTOImpl
import com.example.translations.framework.datasource.implementation.remote.dto.TranslationResponseDTOImpl
import com.example.translations.util.LanguageCode.ENGLISH
import com.example.translations.util.LanguageCode.FRENCH
import com.example.translations.util.LanguageCode.GERMAN
import com.example.translations.util.LanguageCode.RUSSIAN
import com.example.translations.util.ifTrue
import io.reactivex.rxjava3.core.Single

class FakeLocalDataSource : LocalDataSource {

    val languages = mutableListOf<LanguageDTOImpl>()

    val words = mutableListOf<WordDTOImpl>()

    val translations = mutableListOf<TranslationDTOImpl>()

    fun clear() {
        languages.clear()
        words.clear()
        translations.clear()
    }

    override fun getLanguage(id: Long): LanguageDTOImpl? {
        languages.map {
            if (it.id == id) return it
        }
        return null
    }

    override fun getLanguages(): Single<List<LocalLanguageDTO>> = Single.just(languages)

    override fun persistLanguages(languages: List<Language>) {
        this.languages.clear()
        var id = 0L
        languages.map {
            LanguageDTOImpl(it.code, it.name).also { id = ++id }
        }.also {
            this.languages.addAll(it)
        }
    }

    override fun getWord(id: Long): WordDTOImpl = words[id.toInt()]

    override fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean
    ): Single<out WordDTO> = WordDTOImpl(value, languageId, queried)
        .also { it.id = words.size.toLong() }
        .let {
            words.add(it)
            Single.just(it)
        }

    override fun persistTranslation(query: Word, translation: String, translationLanguage: Long) {
        WordDTOImpl(translation, translationLanguage)
            .also {
                it.id = words.size.toLong()
                words.add(it)
                translations.add(
                    TranslationDTOImpl(
                        query.id,
                        it.id,
                        query.language,
                        translationLanguage
                    )
                )
            }

    }

    override fun getTranslation(
        query: Word,
        toLanguage: Language
    ): Single<out WordDTO> {

        fun find(word: Word, languageId: Long): WordDTOImpl? {
            translations.map {
                (it.wordId == word.id && it.toLanguage == languageId).ifTrue<WordDTOImpl> {
                    return words[it.translationId.toInt()]
                }
            }
            return null
        }

        return when (val translation = find(query, toLanguage.id)) {
            null -> Single.error(Exception())
            else -> Single.just(translation)
        }
    }

    override fun getQueriedWords(): Single<List<WordDTO>> {
        val list = mutableListOf<WordDTO>()
        words.map {
            it.queried.ifTrue { list.add(it) }
        }
        return Single.just(list)
    }

    override fun getTranslations(word: WordDTO): List<WordDTO> {
        val list = mutableListOf<TranslationDTOImpl>()
        translations.map {
            (it.wordId == word.id).ifTrue { list.add(it) }
        }
        val output = mutableListOf<WordDTOImpl>()
        list.map { t ->
            words.map { w ->
                (w.id == t.translationId && w.language == t.toLanguage).ifTrue { output.add(w) }
            }
        }
        return output
    }

    override fun deleteEntry(entry: DictionaryEntry) {
        val translationsToRemove = mutableListOf<TranslationDTOImpl>()
        this.translations.map {
            if (it.wordId == entry.word.id) translationsToRemove.add(it)
        }
        this.translations.removeAll(translationsToRemove)
        translations.map {
            words.removeAt(it.id.toInt())
        }
        words.removeAt(entry.word.id.toInt())
    }
}

fun fakeTranslate(word: String) =
    when (word) {
        "кошка" -> "cat"
        "cat" -> "кошка"
        "собака" -> "dog"
        "dog" -> "собака"
        "рыба" -> "fish"
        "fish" -> "рыба"
        "птица" -> "bird"
        "bird" -> "птица"
        "животное" -> "animal"
        "animal" -> "животное"
        else -> throw Exception()
    }


//<editor-fold defaultstate="collapsed" desc="LANGUAGE LISTS">

val fakeLanguagesResponse = LanguagesResponseDTOImpl(
    listOf(
        com.example.translations.framework.datasource.implementation.remote.dto.LanguageDTOImpl(
            RUSSIAN,
            "русский"
        ),
        com.example.translations.framework.datasource.implementation.remote.dto.LanguageDTOImpl(
            ENGLISH,
            "english"
        ),
        com.example.translations.framework.datasource.implementation.remote.dto.LanguageDTOImpl(
            GERMAN,
            "deutsch"
        ),
        com.example.translations.framework.datasource.implementation.remote.dto.LanguageDTOImpl(
            FRENCH,
            "francais"
        )
    )
)

val fakeLocalLanguages = listOf<LanguageDTOImpl>(
    LanguageDTOImpl(RUSSIAN, "русский").also { it.id = 1 },
    LanguageDTOImpl(ENGLISH, "english").also { it.id = 2 },
    LanguageDTOImpl(GERMAN, "deutsch").also { it.id = 3 },
    LanguageDTOImpl(FRENCH, "francais").also { it.id = 4 }
)

val fakeTranslationResponse = TranslationResponseDTOImpl(listOf(com.example.translations.framework.datasource.implementation.remote.dto.TranslationDTOImpl(text = "word", detectedLanguageCode = ENGLISH)))
//</editor-fold>