package com.example.dictionary.domain.usecase.implementation

import com.example.dictionary.domain.usecase.event.implementation.data.DataEvent
import com.example.translations.domain.repository.abstraction.LanguageRepository
import com.example.translations.domain.usecase.abstraction.UseCase
import com.example.translations.domain.usecase.event.Error
import com.example.translations.util.rx.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers.io
import timber.log.Timber

class GetLanguagesUseCase(
    private val repository: LanguageRepository,
    private val rxBus: RxBus
) : UseCase {

    override fun execute() {
        repository.getLanguages()
            .subscribeOn(io())
            .observeOn(io())
            .subscribe(
                { rxBus.send(DataEvent.LanguagesLoaded(it)) },
                { rxBus.send(DataEvent.Failure(Error.CouldNotLoadLanguages)) })
    }
}