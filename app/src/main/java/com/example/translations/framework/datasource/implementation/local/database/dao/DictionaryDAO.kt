package com.example.translations.framework.datasource.implementation.local.database.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.framework.datasource.implementation.local.dto.TranslationDTOImpl
import com.example.translations.framework.datasource.implementation.local.dto.WordDTOImpl
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single

@Dao
abstract class DictionaryDAO {

    @Insert(onConflict = REPLACE)
    abstract fun insertTranslation(translation: TranslationDTOImpl)

    @Insert(onConflict = IGNORE)
    abstract fun insertWord(word: WordDTOImpl) : Completable

    @Query("SELECT * FROM words WHERE id = :id;")
    abstract fun getWord(id: Long): WordDTOImpl

    @Query("SELECT * FROM words WHERE value = :value AND language_id = :languageId;")
    abstract fun getWord(value: String, languageId: Long): Single<WordDTOImpl>

    @Query("SELECT * FROM words WHERE queried = 1 ORDER BY id DESC;")
    abstract fun getDictionary(): Single<List<WordDTOImpl>>

    @RewriteQueriesToDropUnusedColumns
    @Query("SELECT w.id, w.value, w.language_id, w.queried FROM words w JOIN translations t ON t.word_id = :wordId AND t.from_language = :fromLanguage AND t.to_language = :toLanguage WHERE w.id = t.translation_id;")
    abstract fun getTranslation(
        wordId: Long,
        fromLanguage: Long,
        toLanguage: Long
    ): Single<WordDTOImpl>

    @Query("SELECT w.id, w.value, w.language_id, w.queried FROM words w JOIN translations t ON t.word_id = :wordId WHERE w.id = t.translation_id;")
    abstract fun getTranslations(wordId: Long): List<WordDTOImpl>

    @Query("DELETE FROM words where id = :id")
    abstract fun deleteWord(id: Long)

    @Query("DELETE FROM translations where word_id = :id")
    abstract fun deleteTranslations(id: Long)

    @Transaction
    open fun deleteEntry(entry: DictionaryEntry){
        entry.apply {
            deleteTranslations(word.id)
            translations.onEach {
                deleteWord(it.id)
            }
            deleteWord(word.id)
        }
    }
}