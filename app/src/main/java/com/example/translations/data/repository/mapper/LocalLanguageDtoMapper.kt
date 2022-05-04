package com.example.translations.data.repository.mapper

import com.example.translations.data.source.local.dto.LocalLanguageDTO
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class LocalLanguageDtoMapper @Inject constructor() :
    DataMapper<LocalLanguageDTO, Language> {
    override fun map(input: LocalLanguageDTO): Language =
        Language(input.code, input.name, input.id)
}