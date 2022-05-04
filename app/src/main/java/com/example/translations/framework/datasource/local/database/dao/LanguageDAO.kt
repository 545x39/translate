package com.example.translations.framework.datasource.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.translations.framework.datasource.local.dto.LanguageDtoImpl
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single

@Dao
interface LanguageDAO {

    @Insert(onConflict = REPLACE)
    fun insert(languages: List<LanguageDtoImpl>)

    @Query("SELECT code FROM language WHERE id = :id")
    fun getLanguageCode(id: Long) : String?

    @Query("SELECT * FROM language")
    fun getLanguages(): Observable<LanguageDtoImpl>

    @Query("SELECT COUNT(1) FROM language")
    fun getCount(): Int

    @Query("DELETE FROM language")
    fun clear()
}