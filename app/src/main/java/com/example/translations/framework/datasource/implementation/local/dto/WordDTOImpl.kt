package com.example.translations.framework.datasource.implementation.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.Common.ID
import com.example.translations.domain.Common.LANGUAGE_ID
import com.example.translations.domain.Word.QUERIED
import com.example.translations.domain.Word.VALUE
import com.example.translations.framework.datasource.implementation.local.database.TableName.TABLE_WORDS

@Entity(
    tableName = TABLE_WORDS,
    indices = [
        Index(value = [ID], unique = true),
        Index(value = [VALUE], unique = true),
        Index(value = [LANGUAGE_ID])
    ],

)
data class WordDTOImpl(
    @ColumnInfo(name = VALUE)
    override val value: String,
    @ColumnInfo(name = LANGUAGE_ID)
    override val language: Long,
    @ColumnInfo(name = QUERIED)
    override var queried: Boolean = false
) : WordDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    override var id: Long = 0
}
