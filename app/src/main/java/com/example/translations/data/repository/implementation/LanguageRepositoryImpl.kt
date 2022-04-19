package com.example.translations.data.repository.implementation

import com.example.translations.data.datasource.abstraction.local.LocalDataSource
import com.example.translations.data.datasource.abstraction.remote.RemoteDataSource
import com.example.translations.data.repository.implementation.mapper.LanguageListMapper
import com.example.translations.data.repository.implementation.mapper.LanguageResponseMapper
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.abstraction.LanguageRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers.io
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : LanguageRepository {

    override fun getLanguages(): Single<List<Language>> {
        val subject = BehaviorSubject.create<List<Language>>()
        localDataSource.getLanguages()
            .subscribeOn(io())
            .subscribe { local ->
                if (local.isNotEmpty()) {
                    subject.onNext(LanguageListMapper().map(local))
                } else {
                    remoteDataSource.fetchLanguages()
                        .subscribe(
                            {
                                with(LanguageResponseMapper().map(it)) {
                                    localDataSource.persistLanguages(this)
                                    subject.onNext(this)
                                }
                            },
                            { Timber.e(it) }
                        )
                }
            }
        return subject.firstOrError()
    }
}