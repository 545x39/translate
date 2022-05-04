package com.example.translations.data.repository.mapper

import com.example.translations.data.source.remote.dto.LanguagesResponseDTO
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class LanguageResponseMapper @Inject constructor() :
    DataMapper<LanguagesResponseDTO, List<Language>> {

    override fun map(input: LanguagesResponseDTO): List<Language> {
        var id = 0L
        return input.languages
            .filter { !it.name.isNullOrBlank() }
            .map { Language(it.code, it.name!!, ++id) }
    }
}
