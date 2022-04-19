package com.example.dictionary.di

import com.example.dictionary.di.module.UseCaseFactoryModule
import com.example.dictionary.framework.presentation.ui.DictionaryFragment
import com.example.translations.di.AppComponent
import dagger.Component

@DictionaryScope
@Component(
    dependencies = [AppComponent::class],
    modules = [UseCaseFactoryModule::class]
)
interface DictionaryComponent{

    fun inject(fragment: DictionaryFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppComponent): DictionaryComponent
    }
}
