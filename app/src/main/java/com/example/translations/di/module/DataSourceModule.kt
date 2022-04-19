package com.example.translations.di.module

import com.example.translations.data.datasource.abstraction.local.LocalDataSource
import com.example.translations.data.datasource.abstraction.remote.RemoteDataSource
import com.example.translations.framework.datasource.implementation.local.RoomDataSource
import com.example.translations.framework.datasource.implementation.remote.YandexTranslateDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: YandexTranslateDataSource): RemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): LocalDataSource

}