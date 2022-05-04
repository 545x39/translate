package com.example.translations.framework.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.translations.data.source.local.dto.LocalLanguageDTO

@Entity(
    tableName = "language",
    indices = [Index(value = ["id"], unique = true)]
)
data class LanguageDtoImpl(
    @ColumnInfo(name = "code")
    override val code: String,
    @ColumnInfo(name = "name")
    override val name: String = ""
) : LocalLanguageDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Long = 0
}