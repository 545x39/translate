package com.example.translations.data.repository.implementation.mapper

import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.abstraction.DataMapper

class WordMapper : DataMapper<WordDTO, Word> {
    override fun map(input: WordDTO) = Word(input.value, input.language, input.id)
}