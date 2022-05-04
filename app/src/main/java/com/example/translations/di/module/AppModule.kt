package com.example.translations.di.module

import android.content.Context
import com.example.translations.util.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val context : Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Singleton
    @Provides
    fun provideRxBus(): RxBus = RxBus()
}