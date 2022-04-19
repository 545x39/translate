package com.example.translations.data.repository.implementation.mapper

import com.example.translations.data.datasource.abstraction.remote.dto.TranslationResponseDTO
import com.example.translations.domain.repository.abstraction.DataMapper

class TranslationResponseMapper : DataMapper<TranslationResponseDTO, String> {

    override fun map(input: TranslationResponseDTO) =
        if (input.translations.isEmpty()) "" else input.translations[0].text
}