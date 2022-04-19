package com.example.translations.framework.datasource.implementation.remote.dto

import com.example.translations.data.datasource.abstraction.remote.dto.TranslationRequestDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TranslationRequestDTOImpl(
    @SerializedName("sourceLanguageCode")
    @Expose
    override val fromLanguage: String,
    @SerializedName("targetLanguageCode")
    @Expose
    override val toLanguage: String,
    @SerializedName("texts")
    @Expose
    override val texts: List<String>
) :TranslationRequestDTO