package com.example.translations.data.repository.mapper

import com.example.translations.data.source.local.dto.WordDTO
import com.example.translations.domain.entity.DictionaryEntry
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class DictionaryEntryMapper @Inject constructor(private val mapper: WordMapper) :
    DataMapper<Pair<WordDTO, List<WordDTO>>, DictionaryEntry> {

    override fun map(input: Pair<WordDTO, List<WordDTO>>): DictionaryEntry {
        val (word, translations) = input
        return DictionaryEntry(
            mapper.map(word),
            translations.map { mapper.map(it) }
        )
    }
}