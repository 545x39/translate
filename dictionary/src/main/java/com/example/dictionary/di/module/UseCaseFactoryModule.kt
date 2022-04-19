package com.example.dictionary.di.module

import com.example.dictionary.domain.usecase.factory.abstraction.UseCaseFactory
import com.example.dictionary.domain.usecase.factory.implementation.UseCaseFactoryImpl
import dagger.Binds
import dagger.Module

@Module
interface UseCaseFactoryModule {

    @Binds
    fun provideFactory(factory: UseCaseFactoryImpl): UseCaseFactory
}