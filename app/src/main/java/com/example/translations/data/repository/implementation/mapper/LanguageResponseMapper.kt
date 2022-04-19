package com.example.translations.data.repository.implementation.mapper

import com.example.translations.data.datasource.abstraction.remote.dto.LanguagesResponseDTO
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.abstraction.DataMapper
import com.example.translations.util.ifFalse

class LanguageResponseMapper
    : DataMapper<LanguagesResponseDTO, List<Language>> {
    override fun map(input: LanguagesResponseDTO) =
        mutableListOf<Language>().apply {
            var id = 0L
            input.languages.map {
                it.name.isNullOrBlank().ifFalse {
                    add(Language(it.code, it.name!!, ++id))
                }
            }
        }
}
