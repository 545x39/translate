package com.example.translations.data.repository.mapper

import com.example.translations.data.source.local.dto.LocalLanguageDTO
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class LanguageListMapper @Inject constructor() :
    DataMapper<List<LocalLanguageDTO>, List<Language>> {

    override fun map(input: List<LocalLanguageDTO>) =
        input.map { Language(it.code, it.name, it.id) }
}