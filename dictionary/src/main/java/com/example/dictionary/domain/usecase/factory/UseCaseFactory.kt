package com.example.dictionary.domain.usecase.factory

import com.example.dictionary.domain.DeleterEntryUseCase
import com.example.dictionary.domain.GetDictionaryUseCase
import com.example.dictionary.domain.GetLanguagesUseCase
import com.example.dictionary.domain.TranslateUseCase
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language

interface UseCaseFactory {

    fun getLanguagesUseCase(): GetLanguagesUseCase

    fun getDictionaryUseCase(query: String? = null): GetDictionaryUseCase

    fun translateUseCase(query: String, fromLanguage: Language, toLanguage: Language): TranslateUseCase

    fun deleteEntryUseCase(entry: DictionaryEntry): DeleterEntryUseCase

}