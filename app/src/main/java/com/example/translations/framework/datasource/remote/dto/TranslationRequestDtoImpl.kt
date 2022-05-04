package com.example.translations.framework.datasource.remote.dto

import com.example.translations.data.source.remote.dto.TranslationRequestDTO
import com.google.gson.annotations.SerializedName

data class TranslationRequestDtoImpl(
    @SerializedName("sourceLanguageCode")
    override val fromLanguage: String,
    @SerializedName("targetLanguageCode")
    override val toLanguage: String,
    @SerializedName("texts")
    override val texts: List<String>
) :TranslationRequestDTO