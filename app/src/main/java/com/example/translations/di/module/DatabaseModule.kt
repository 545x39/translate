package com.example.translations.di.module

import android.content.Context
import androidx.room.Room
import com.example.translations.framework.datasource.implementation.local.database.DB_DIR
import com.example.translations.framework.datasource.implementation.local.database.DB_FILENAME
import com.example.translations.framework.datasource.implementation.local.database.Database
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoom(context: Context) =
        Room.databaseBuilder(
            context, Database::class.java,
            "${context.filesDir}${File.separator}$DB_DIR${File.separator}$DB_FILENAME"
        ).fallbackToDestructiveMigration().build()

}
