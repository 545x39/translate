package com.example.translations.framework.datasource.implementation.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.translations.data.datasource.abstraction.local.dto.LocalLanguageDTO
import com.example.translations.domain.Common.ID
import com.example.translations.domain.Language.CODE
import com.example.translations.domain.Language.NAME
import com.example.translations.framework.datasource.implementation.local.database.TableName.TABLE_LANGUAGE
import com.google.gson.annotations.Expose

@Entity(
    tableName = TABLE_LANGUAGE,
    indices = [Index(value = [ID], unique = true)]
)
data class LanguageDTOImpl(
    @ColumnInfo(name = CODE)
    @Expose
    override val code: String,
    @ColumnInfo(name = NAME)
    @Expose
    override val name: String = ""
) : LocalLanguageDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    override var id: Long = 0
}