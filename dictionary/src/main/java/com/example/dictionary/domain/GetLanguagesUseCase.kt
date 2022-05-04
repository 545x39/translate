package com.example.dictionary.domain

import com.example.dictionary.domain.usecase.event.data.DataEvent
import com.example.translations.domain.repository.LanguageRepository
import com.example.translations.domain.usecase.UseCase
import com.example.translations.domain.usecase.event.Error
import com.example.translations.util.RxBus
import io.reactivex.rxjava3.schedulers.Schedulers

class GetLanguagesUseCase(
    private val repository: LanguageRepository,
    private val rxBus: RxBus
) : UseCase {

    override fun execute() {
        repository.getLanguages()
            .subscribeOn(Schedulers.io())
            .subscribe(
                { rxBus.send(DataEvent.LanguagesLoaded(it)) },
                { rxBus.send(DataEvent.Failure(Error.CouldNotLoadLanguages)) })
    }
}