package com.example.translations.framework.datasource.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.translations.data.source.local.dto.WordDTO

@Entity(
    tableName = "words",
    indices = [
        Index(value = ["id"], unique = true),
        Index(value = ["value"], unique = true)
    ],

)
data class WordDtoImpl(
    @ColumnInfo(name = "value")
    override val value: String,
    @ColumnInfo(name = "language_id")
    override val language: Long,
    @ColumnInfo(name = "queried")
    override var queried: Boolean = false
) : WordDTO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    override var id: Long = 0
}
