package com.example.dictionary.domain.usecase.event.implementation.data

import com.example.translations.domain.usecase.event.abstraction.EventType

enum class DataEventType: EventType {
    LOADING,
    ERROR,
    LANGUAGES_LOADED,
    DICTIONARY_LOADED,
    QUERY_SAVED,
    TRANSLATION_COMPLETED,
    ENTRY_DELETED
}