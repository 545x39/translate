package com.example.translations.di.module

import android.content.Context
import com.example.translations.App
import com.example.translations.util.rx.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app :App) {

    @Singleton
    @Provides
    fun provideContext(): Context = app.baseContext

    @Singleton
    @Provides
    fun provideRxBus(): RxBus = RxBus()
}