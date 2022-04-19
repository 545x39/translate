package com.example.translations.framework.datasource.implementation.local.database

import androidx.room.RoomDatabase
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.TranslationDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import com.example.translations.framework.datasource.implementation.local.database.dao.DictionaryDAO
import com.example.translations.framework.datasource.implementation.local.database.dao.LanguageDAO

@androidx.room.Database(
    entities = [
        WordDTOImpl::class,
        TranslationDTOImpl::class,
        LanguageDTOImpl::class
    ],
    version = DB_VERSION,
    exportSchema = false
)
abstract class Database : RoomDatabase() {

    abstract fun languageDAO(): LanguageDAO

    abstract fun dictionaryDAO(): DictionaryDAO
}