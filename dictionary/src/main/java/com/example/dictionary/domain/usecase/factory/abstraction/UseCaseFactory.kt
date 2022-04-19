package com.example.dictionary.domain.usecase.factory.abstraction

import com.example.dictionary.domain.usecase.implementation.DeleterEntryUseCase
import com.example.dictionary.domain.usecase.implementation.GetDictionaryUseCase
import com.example.dictionary.domain.usecase.implementation.GetLanguagesUseCase
import com.example.dictionary.domain.usecase.implementation.TranslateUseCase
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language

interface UseCaseFactory {

    fun getLanguagesUseCase(): GetLanguagesUseCase

    fun getDictionaryUseCase(query: String? = null): GetDictionaryUseCase

    fun translateUseCase(query: String, fromLanguage: Language, toLanguage: Language): TranslateUseCase

    fun deleteEntryUseCase(entry: DictionaryEntry): DeleterEntryUseCase

}