package com.example.translations.data.repository.implementation

import com.example.translations.data.datasource.abstraction.local.LocalDataSource
import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.data.datasource.abstraction.remote.RemoteDataSource
import com.example.translations.data.repository.implementation.mapper.DictionaryEntryMapper
import com.example.translations.data.repository.implementation.mapper.TranslationResponseMapper
import com.example.translations.data.repository.implementation.mapper.WordMapper
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers.io
import io.reactivex.rxjava3.subjects.BehaviorSubject
import timber.log.Timber
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : DictionaryRepository {

    override fun persistWord(word: String, language: Long, queried: Boolean): Single<Word> =
        localDataSource.saveAndGetWord(word, language, queried)
            .subscribeOn(io())
            .map { WordMapper().map(it) }

    override fun getDictionary(query: String?): Single<List<DictionaryEntry>> =
        localDataSource.getQueriedWords()
            .subscribeOn(io())
            .map {
                it.map { word ->
                    DictionaryEntryMapper().map(word to localDataSource.getTranslations(word))
                }.filter { entry ->
                    query.isNullOrBlank()
                            || entry.word.value == query
                            || entry.translations.contain(query)
                }
            }

    override fun translate(
        query: Word,
        fromLanguage: Language,
        toLanguage: Language
    ): Single<String> {
        val subject = BehaviorSubject.create<String>()
        localDataSource.getTranslation(query, toLanguage)
            .subscribeOn(io())
            .subscribe(
                { subject.onNext(it.value) },
                {
                    remoteDataSource.fetchTranslation(
                        query.value,
                        fromLanguage.code,
                        toLanguage.code
                    ).subscribe({ response ->
                        TranslationResponseMapper().map(response).also { translation ->
                            localDataSource.persistTranslation(
                                query,
                                translation,
                                toLanguage.id
                            )
                            subject.onNext(translation)
                        }
                    },
                        { Timber.e(it) })
                }
            )
        return subject.firstOrError()
    }

    override fun deleteEntry(entry: DictionaryEntry): Completable =
        Completable.fromAction { localDataSource.deleteEntry(entry) }

    private fun List<Word>.contain(query: String): Boolean {
        map { if (it.value == query) return true }
        return false
    }

}