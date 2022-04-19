package com.example.translations.di.module

import com.example.translations.data.repository.implementation.DictionaryRepositoryImpl
import com.example.translations.data.repository.implementation.LanguageRepositoryImpl
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.repository.abstraction.LanguageRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideLanguageRepository(repository: LanguageRepositoryImpl): LanguageRepository

    @Binds
    fun provideDictionaryRepository(repository: DictionaryRepositoryImpl): DictionaryRepository
}