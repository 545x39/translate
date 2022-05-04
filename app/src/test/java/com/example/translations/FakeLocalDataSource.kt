package com.example.translations

import com.example.translations.data.source.local.LocalDataSource
import com.example.translations.data.source.local.dto.LocalLanguageDTO
import com.example.translations.data.source.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.framework.datasource.local.dto.LanguageDtoImpl
import com.example.translations.framework.datasource.local.dto.TranslationDtoImpl
import com.example.translations.framework.datasource.local.dto.WordDtoImpl
import com.example.translations.framework.datasource.remote.dto.LanguagesResponseDtoImpl
import com.example.translations.framework.datasource.remote.dto.TranslationResponseDtoImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

private const val RUSSIAN = "ru"
private const val ENGLISH = "en"
private const val GERMAN = "de"
private const val FRENCH = "fr"

class FakeLocalDataSource : LocalDataSource {

    val languages = mutableListOf<LanguageDtoImpl>()

    val words = mutableListOf<WordDtoImpl>()

    val translations = mutableListOf<TranslationDtoImpl>()

    fun clear() {
        languages.clear()
        words.clear()
        translations.clear()
    }

    override fun getLanguages(): Observable<LocalLanguageDTO> = Observable.fromIterable(languages)

    override fun persistLanguages(languageList: List<Language>) {
        languages.clear()
        var id = 0L
        languageList.map {
            LanguageDtoImpl(it.code, it.name).also { lng -> lng.id = ++id }
        }.let { languages.addAll(it) }
    }

    override fun getWord(id: Long): WordDtoImpl = words[id.toInt()]

    override fun saveAndGetWord(
        value: String,
        languageId: Long,
        queried: Boolean
    ): Single<out WordDTO> = Single.just(WordDtoImpl(value, languageId, queried).also {
        it.id = words.size.toLong()
        words.add(it)
    })

    override fun persistTranslation(query: Word, translation: String, translationLanguage: Long) {
        WordDtoImpl(translation, translationLanguage)
            .also {
                it.id = words.size.toLong()
                words.add(it)
                translations.add(
                    TranslationDtoImpl(
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
    ): Observable<out WordDTO> =
        translations.find { it.wordId == query.id && it.toLanguage == toLanguage.id }
            ?.let { words[it.translationId.toInt()] }
            ?.let { Observable.just(it) } ?: Observable.empty()

    override fun getQueriedWords(): Observable<out WordDTO> =
        Observable.fromIterable(words.filter { it.queried })

    override fun getTranslations(word: WordDTO): List<WordDTO> =
        translations.filter { it.wordId == word.id }
            .mapNotNull { t ->
                words.find { it.id == t.translationId && it.language == t.toLanguage }
            }

    override fun deleteEntry(entry: DictionaryEntry) {}
}

val fakeLanguagesResponse = LanguagesResponseDtoImpl(
    listOf(
        com.example.translations.framework.datasource.remote.dto.LanguageDtoImpl(
            RUSSIAN,
            "русский"
        ),
        com.example.translations.framework.datasource.remote.dto.LanguageDtoImpl(
            ENGLISH,
            "english"
        ),
        com.example.translations.framework.datasource.remote.dto.LanguageDtoImpl(
            GERMAN,
            "deutsch"
        ),
        com.example.translations.framework.datasource.remote.dto.LanguageDtoImpl(
            FRENCH,
            "francais"
        )
    )
)

val fakeLocalLanguages = listOf(
    LanguageDtoImpl(RUSSIAN, "русский").also { it.id = 1 },
    LanguageDtoImpl(ENGLISH, "english").also { it.id = 2 },
    LanguageDtoImpl(GERMAN, "deutsch").also { it.id = 3 },
    LanguageDtoImpl(FRENCH, "francais").also { it.id = 4 }
)

val fakeTranslationResponse = TranslationResponseDtoImpl(
    listOf(
        com.example.translations.framework.datasource.remote.dto.TranslationDTOImpl(
            text = "word",
            detectedLanguageCode = ENGLISH
        )
    )
)