package com.example.translations.framework.datasource.implementation.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.translations.framework.datasource.implementation.local.dto.LanguageDTOImpl
import io.reactivex.rxjava3.core.Single

@Dao
interface LanguageDAO {

    @Insert(onConflict = REPLACE)
    fun insert(languages: List<LanguageDTOImpl>)

    @Query("SELECT * FROM language WHERE id = :id")
    fun getLanguage(id: Long) : LanguageDTOImpl

    @Query("SELECT code FROM language WHERE id = :id")
    fun getLanguageCode(id: Long) : String?

    @Query("SELECT * FROM language")
    fun getLanguages(): Single<List<LanguageDTOImpl>>

    @Query("SELECT COUNT(1) FROM language")
    fun getCount(): Int

    @Query("DELETE FROM language")
    fun clear()
}