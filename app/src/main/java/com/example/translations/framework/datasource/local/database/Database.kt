package com.example.translations.framework.datasource.local.database

import androidx.room.RoomDatabase
import com.example.translations.framework.datasource.local.dto.LanguageDtoImpl
import com.example.translations.framework.datasource.local.dto.TranslationDtoImpl
import com.example.translations.framework.datasource.local.dto.WordDtoImpl
import com.example.translations.framework.datasource.local.database.dao.DictionaryDAO
import com.example.translations.framework.datasource.local.database.dao.LanguageDAO

private const val DB_VERSION = 1

@androidx.room.Database(
    entities = [
        WordDtoImpl::class,
        TranslationDtoImpl::class,
        LanguageDtoImpl::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun languageDAO(): LanguageDAO

    abstract fun dictionaryDAO(): DictionaryDAO
}