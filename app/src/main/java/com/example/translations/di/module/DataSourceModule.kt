package com.example.translations.di.module

import com.example.translations.data.source.local.LocalDataSource
import com.example.translations.data.source.remote.RemoteDataSource
import com.example.translations.framework.datasource.local.RoomDataSource
import com.example.translations.framework.datasource.remote.YandexTranslateDataSource
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun provideRemoteDataSource(dataSource: YandexTranslateDataSource): RemoteDataSource

    @Binds
    fun provideLocalDataSource(dataSource: RoomDataSource): LocalDataSource

}