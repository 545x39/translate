package com.example.translations.util

import com.example.translations.domain.usecase.event.Event
import io.reactivex.rxjava3.subjects.PublishSubject

class RxBus {

    private val subject: PublishSubject<Event> = PublishSubject.create()

    fun send(event: Event) {
        subject.onNext(event)
    }

    fun get() = subject

}