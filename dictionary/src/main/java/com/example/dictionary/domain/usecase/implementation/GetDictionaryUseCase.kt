package com.example.dictionary.domain.usecase.implementation

import com.example.dictionary.domain.usecase.event.implementation.data.DataEvent
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.usecase.abstraction.UseCase
import com.example.translations.domain.usecase.event.Error
import com.example.translations.util.rx.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers.io
import timber.log.Timber

class GetDictionaryUseCase(
    private val repository: DictionaryRepository,
    private val rxBus: RxBus,
    private val query: String? = null
) : UseCase {

    override fun execute() {
        rxBus.send(DataEvent.Loading)
        repository.getDictionary(query)
            .observeOn(io())
            .subscribe(
                { rxBus.send(DataEvent.DictionaryLoaded(it)) },
                { rxBus.send(DataEvent.DictionaryLoaded(listOf())) }
            )
    }
}