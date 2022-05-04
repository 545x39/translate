package com.example.translations.data.repository

import com.example.translations.data.repository.mapper.DictionaryEntryMapper
import com.example.translations.data.repository.mapper.TranslationResponseMapper
import com.example.translations.data.repository.mapper.WordMapper
import com.example.translations.data.source.local.LocalDataSource
import com.example.translations.data.source.remote.RemoteDataSource
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.DictionaryRepository
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class DictionaryRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val wordMapper: WordMapper,
    private val entryMapper: DictionaryEntryMapper,
    private val responseMapper: TranslationResponseMapper
) : DictionaryRepository {

    override fun persistWord(word: String, language: Long, queried: Boolean): Single<Word> =
        localDataSource.saveAndGetWord(word, language, queried)
            .subscribeOn(Schedulers.io())
            .map { wordMapper.map(it) }

    override fun getDictionary(query: String?): Single<List<DictionaryEntry>> =
        localDataSource.getQueriedWords()
            .map { entryMapper.map(it to localDataSource.getTranslations(it)) }
            .filter {
                query.isNullOrBlank()
                        || it.word.value == query
                        || it.translations.any { e -> e.value == query }
            }.toList()

    override fun translate(
        query: Word,
        fromLanguage: Language,
        toLanguage: Language
    ): Single<String> = localDataSource.getTranslation(query, toLanguage)
        .map { it.value }
        .switchIfEmpty(Observable.defer { download(query, fromLanguage, toLanguage) })
        .firstOrError()

    private fun download(
        query: Word,
        fromLanguage: Language,
        toLanguage: Language
    ): Observable<String> = remoteDataSource.fetchTranslation(
        query.value,
        fromLanguage.code,
        toLanguage.code
    ).doOnNext {
        responseMapper.map(it)
            .also { translation ->
                localDataSource.persistTranslation(
                    query,
                    translation,
                    toLanguage.id
                )
            }
    }.map { it.translations[0].text }

    override fun deleteEntry(entry: DictionaryEntry): Completable =
        Completable.fromAction { localDataSource.deleteEntry(entry) }

}