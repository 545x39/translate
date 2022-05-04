package com.example.dictionary.di.module

import com.example.dictionary.domain.usecase.factory.UseCaseFactory
import com.example.dictionary.domain.usecase.factory.UseCaseFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseFactoryModule {

    @Binds
    fun provideFactory(factory: UseCaseFactoryImpl): UseCaseFactory
}