package com.example.dictionary.domain

import com.example.dictionary.domain.usecase.event.data.DataEvent
import com.example.translations.domain.repository.DictionaryRepository
import com.example.translations.domain.usecase.UseCase
import com.example.translations.util.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers

class GetDictionaryUseCase(
    private val repository: DictionaryRepository,
    private val rxBus: RxBus,
    private val query: String? = null
) : UseCase {

    override fun execute() {
        rxBus.send(DataEvent.Loading)
        repository.getDictionary(query)
            .subscribeOn(Schedulers.io())
            .subscribe(
                { rxBus.send(DataEvent.DictionaryLoaded(it)) },
                { rxBus.send(DataEvent.DictionaryLoaded(listOf())) }
            )
    }
}