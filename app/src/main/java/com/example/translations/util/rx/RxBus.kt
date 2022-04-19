package com.example.translations.util.rx

import com.example.translations.domain.usecase.event.abstraction.Event
import io.reactivex.rxjava3.subjects.PublishSubject
import timber.log.Timber

class RxBus {

    private val subject: PublishSubject<Event> = PublishSubject.create()

    fun send(event: Event) {
        subject.onNext(event)
    }

    fun get() = subject

}