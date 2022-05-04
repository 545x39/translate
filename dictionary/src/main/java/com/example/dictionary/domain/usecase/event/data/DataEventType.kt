package com.example.dictionary.domain.usecase.event.data

import com.example.translations.domain.usecase.event.EventType

enum class DataEventType: EventType {
    LOADING,
    ERROR,
    LANGUAGES_LOADED,
    DICTIONARY_LOADED,
    QUERY_SAVED,
    TRANSLATION_COMPLETED,
    ENTRY_DELETED
}