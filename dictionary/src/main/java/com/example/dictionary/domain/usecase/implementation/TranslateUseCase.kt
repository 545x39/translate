package com.example.dictionary.domain.usecase.implementation

import com.example.dictionary.domain.usecase.event.implementation.data.DataEvent
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.usecase.abstraction.UseCase
import com.example.translations.domain.usecase.event.Error
import com.example.translations.util.rx.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers.io
import timber.log.Timber
import java.util.concurrent.TimeUnit

class TranslateUseCase(
    private val query: String,
    private val fromLanguage: Language,
    private val toLanguage: Language,
    private val repository: DictionaryRepository,
    private val rxBus: RxBus
) : UseCase {

    override fun execute() {
        rxBus.send(DataEvent.Loading)
        repository.persistWord(query, fromLanguage.id, true)
            .subscribeOn(io())
            .flatMap {
                rxBus.send(DataEvent.QuerySaved)
                repository.translate(it, fromLanguage, toLanguage)
            }
            .subscribe(
                { rxBus.send(DataEvent.TranslationCompleted(it)) },
                { rxBus.send(DataEvent.Failure(Error.TranslationNotFound)) }
            )
    }
}