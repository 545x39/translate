package com.example.dictionary.domain.usecase.event.view

import com.example.translations.domain.usecase.event.EventType

enum class ViewEventType: EventType {
    LOAD_LANGUAGES,
    LOAD_DICTIONARY,
    DELETE_WORD,
    TRANSLATE,
    ADD_TO_FAVORITES,
    DELETE_FROM_FAVORITES
}