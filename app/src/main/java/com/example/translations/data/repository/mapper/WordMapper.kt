package com.example.translations.data.repository.mapper

import com.example.translations.data.source.local.dto.WordDTO
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class WordMapper @Inject constructor() : DataMapper<WordDTO, Word> {
    override fun map(input: WordDTO) = Word(input.value, input.language, input.id)
}