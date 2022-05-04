package com.example.translations.di.module

import com.example.translations.data.repository.DictionaryRepositoryImpl
import com.example.translations.data.repository.LanguageRepositoryImpl
import com.example.translations.domain.repository.DictionaryRepository
import com.example.translations.domain.repository.LanguageRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun provideLanguageRepository(repository: LanguageRepositoryImpl): LanguageRepository

    @Binds
    fun provideDictionaryRepository(repository: DictionaryRepositoryImpl): DictionaryRepository
}