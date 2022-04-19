package com.example.translations.data.repository.implementation.mapper

import com.example.translations.data.datasource.abstraction.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.entity.Word
import com.example.translations.domain.repository.abstraction.DataMapper

class DictionaryEntryMapper : DataMapper<Pair<WordDTO, List<WordDTO>>, DictionaryEntry> {

    override fun map(input: Pair<WordDTO, List<WordDTO>>): DictionaryEntry {
        val mapper = WordMapper()
        val (word, translations) = input
        return DictionaryEntry(
            mapper.map(word),
            mutableListOf<Word>().apply {
                translations.map { add(mapper.map(it)) }
            }
        )
    }
}