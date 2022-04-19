package com.example.dictionary.domain.usecase.event.implementation.view

import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.entity.Word
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import com.example.translations.domain.usecase.event.abstraction.Event

sealed class ViewEvent : Event {

    object GetLanguages : ViewEvent() {
        override val type = ViewEventType.LOAD_LANGUAGES
    }

    class GetDictionary(val query: String? = null) : ViewEvent() {
        override val type = ViewEventType.LOAD_DICTIONARY
    }

    class DeleteEntry(val entry: DictionaryEntry) : ViewEvent() {
        override val type = ViewEventType.DELETE_WORD
    }

    class Translate(val query: String, val fromLanguage: Language, val toLanguage: Language) : ViewEvent() {
        override val type = ViewEventType.TRANSLATE
    }
}
