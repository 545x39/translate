package com.example.translations.framework.datasource.implementation.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.translations.data.datasource.abstraction.local.dto.TranslationDTO
import com.example.translations.domain.Common.ID
import com.example.translations.domain.Common.WORD_ID
import com.example.translations.domain.Translation.FROM_LANGUAGE
import com.example.translations.domain.Translation.TO_LANGUAGE
import com.example.translations.domain.Translation.TRANSLATION_ID
import com.example.translations.framework.datasource.implementation.local.database.TableName.TABLE_TRANSLATIONS

@Entity(tableName = TABLE_TRANSLATIONS)
data class TranslationDTOImpl(
    @ColumnInfo(name = WORD_ID)
    override val wordId: Long,
    @ColumnInfo(name = TRANSLATION_ID)
    override val translationId: Long,
    @ColumnInfo(name = FROM_LANGUAGE)
    override val fromLanguage: Long,
    @ColumnInfo(name = TO_LANGUAGE)
    override val toLanguage: Long,
) : TranslationDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    override var id: Int = 0
}
