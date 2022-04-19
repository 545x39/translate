package com.example.dictionary.domain.usecase.implementation

import com.example.dictionary.domain.usecase.event.implementation.data.DataEvent
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.usecase.abstraction.UseCase
import com.example.translations.util.rx.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers.io

class DeleterEntryUseCase(
    private val entry: DictionaryEntry,
    private val repository: DictionaryRepository,
    private val rxBus: RxBus
) : UseCase {
    override fun execute() {
        repository.deleteEntry(entry)
            .subscribeOn(io())
            .subscribe({ rxBus.send(DataEvent.EntryDeleted) }, {})
    }
}