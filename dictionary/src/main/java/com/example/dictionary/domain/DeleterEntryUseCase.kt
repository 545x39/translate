package com.example.dictionary.domain

import com.example.dictionary.domain.usecase.event.data.DataEvent
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.repository.DictionaryRepository
import com.example.translations.domain.usecase.UseCase
import com.example.translations.util.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers

class DeleterEntryUseCase(
    private val entry: DictionaryEntry,
    private val repository: DictionaryRepository,
    private val rxBus: RxBus
) : UseCase {
    override fun execute() {
        repository.deleteEntry(entry)
            .subscribeOn(Schedulers.io())
            .subscribe({ rxBus.send(DataEvent.EntryDeleted) }, {})
    }
}