package com.example.translations.di.module

import android.content.Context
import androidx.room.Room
import com.example.translations.framework.datasource.local.database.Database
import dagger.Module
import dagger.Provides
import java.io.File
import javax.inject.Singleton

private const val DB_DIR = "db"
private const val DB_FILENAME = "translations.db"

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
