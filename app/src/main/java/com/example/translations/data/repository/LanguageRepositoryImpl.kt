package com.example.translations.data.repository

import com.example.translations.data.repository.mapper.LanguageResponseMapper
import com.example.translations.data.repository.mapper.LocalLanguageDtoMapper
import com.example.translations.data.source.local.LocalDataSource
import com.example.translations.data.source.remote.RemoteDataSource
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.LanguageRepository
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import timber.log.Timber
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val languageResponseMapper: LanguageResponseMapper,
    private val localLanguagesMapper: LocalLanguageDtoMapper
) : LanguageRepository {

    override fun getLanguages(): Single<List<Language>> {
        return localDataSource.getLanguages()
            .map { localLanguagesMapper.map(it) }
            .switchIfEmpty(Observable.defer {
                remoteDataSource.fetchLanguages()
                    .map { languageResponseMapper.map(it) }
                    .doOnNext { localDataSource.persistLanguages(it) }
                    .concatMapIterable { it }
            }
            ).toList()
    }
}