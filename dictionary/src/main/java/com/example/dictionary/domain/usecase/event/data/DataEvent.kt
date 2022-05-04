package com.example.dictionary.domain.usecase.event.data

import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.usecase.event.Error
import com.example.translations.domain.usecase.event.Event

sealed class DataEvent : Event {

    object Loading : DataEvent() {
        override val type = DataEventType.LOADING
    }

    class Failure(val error: Error = Error.Unknown) : DataEvent() {
        override val type = DataEventType.ERROR
    }

    class LanguagesLoaded(val languages: List<Language>) : DataEvent() {
        override val type = DataEventType.LANGUAGES_LOADED
    }

    object QuerySaved : DataEvent() {
        override val type = DataEventType.DICTIONARY_LOADED
    }

    class DictionaryLoaded(val entries: List<DictionaryEntry>) : DataEvent() {
        override val type = DataEventType.DICTIONARY_LOADED
    }

    class TranslationCompleted(val translation: String) : DataEvent() {
        override val type = DataEventType.TRANSLATION_COMPLETED
    }

    object EntryDeleted : DataEvent() {
        override val type = DataEventType.ENTRY_DELETED
    }
}
