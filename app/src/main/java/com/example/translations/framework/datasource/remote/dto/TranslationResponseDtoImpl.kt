package com.example.translations.framework.datasource.remote.dto

import com.example.translations.data.source.remote.dto.TranslationDto
import com.example.translations.data.source.remote.dto.TranslationResponseDTO

import com.google.gson.annotations.SerializedName

data class TranslationResponseDtoImpl(
    @SerializedName("translations")
    override val translations: List<TranslationDTOImpl>) : TranslationResponseDTO

data class TranslationDTOImpl(
    @SerializedName("text")
    override val text: String,
    @SerializedName("detectedLanguageCode")
    override val detectedLanguageCode: String
) : TranslationDto