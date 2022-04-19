package com.example.translations.data.repository.implementation.mapper

import com.example.translations.data.datasource.abstraction.local.dto.LocalLanguageDTO
import com.example.translations.domain.entity.Language
import com.example.translations.domain.repository.abstraction.DataMapper

class LanguageListMapper : DataMapper<List<LocalLanguageDTO>, List<Language>> {

    override fun map(input: List<LocalLanguageDTO>): List<Language> =
        mutableListOf<Language>().apply {
            input.map { add(Language(it.code, it.name, it.id)) }
        }
}