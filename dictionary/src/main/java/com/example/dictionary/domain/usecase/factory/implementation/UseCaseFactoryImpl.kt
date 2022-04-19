package com.example.dictionary.domain.usecase.factory.implementation

import com.example.dictionary.domain.usecase.factory.abstraction.UseCaseFactory
import com.example.dictionary.domain.usecase.implementation.DeleterEntryUseCase
import com.example.dictionary.domain.usecase.implementation.GetDictionaryUseCase
import com.example.dictionary.domain.usecase.implementation.GetLanguagesUseCase
import com.example.dictionary.domain.usecase.implementation.TranslateUseCase
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.repository.abstraction.LanguageRepository
import com.example.translations.util.rx.RxBus
import javax.inject.Inject

class UseCaseFactoryImpl @Inject constructor(
    private val languageRepository: LanguageRepository,
    private val dictionaryRepository: DictionaryRepository,
    private val rxBus: RxBus
) : UseCaseFactory {

    override fun getLanguagesUseCase() = GetLanguagesUseCase(languageRepository, rxBus)

    override fun getDictionaryUseCase(query: String?): GetDictionaryUseCase = GetDictionaryUseCase(dictionaryRepository, rxBus, query)

    override fun translateUseCase(
        query: String,
        fromLanguage: Language,
        toLanguage: Language
    ): TranslateUseCase = TranslateUseCase(query, fromLanguage, toLanguage, dictionaryRepository, rxBus)

    override fun deleteEntryUseCase(entry: DictionaryEntry) = DeleterEntryUseCase(entry, dictionaryRepository, rxBus)

}