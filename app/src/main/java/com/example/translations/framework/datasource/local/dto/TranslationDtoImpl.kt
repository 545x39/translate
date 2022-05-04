package com.example.translations.framework.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.translations.data.source.local.dto.TranslationDTO

@Entity(tableName = "translations")
data class TranslationDtoImpl(
    @ColumnInfo(name = "word_id")
    override val wordId: Long,
    @ColumnInfo(name = "translation_id")
    override val translationId: Long,
    @ColumnInfo(name = "from_language")
    override val fromLanguage: Long,
    @ColumnInfo(name = "to_language")
    override val toLanguage: Long,
) : TranslationDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Int = 0
}
