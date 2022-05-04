package com.example.translations.data.repository.mapper

import com.example.translations.data.source.remote.dto.TranslationResponseDTO
import com.example.translations.domain.repository.DataMapper
import javax.inject.Inject

class TranslationResponseMapper @Inject constructor() : DataMapper<TranslationResponseDTO, String> {

    override fun map(input: TranslationResponseDTO) =
        input.translations.firstOrNull()?.text ?: ""

}