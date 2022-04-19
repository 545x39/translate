package com.example.translations.di

import android.content.Context
import com.example.translations.di.module.*
import com.example.translations.domain.repository.abstraction.DictionaryRepository
import com.example.translations.domain.repository.abstraction.LanguageRepository
import com.example.translations.util.rx.RxBus
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkModule::class,
        DatabaseModule::class,
        DataSourceModule::class,
        RepositoryModule::class
    ]
)
interface AppComponent {

    fun inject(app: Context)

    fun getLanguageRepository(): LanguageRepository

    fun getDictionaryRepository(): DictionaryRepository

    fun getRxBus(): RxBus

    @Component.Factory
    interface Factory {
        fun create(appModule: AppModule): AppComponent
    }
}