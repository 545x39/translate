package com.example.translations.framework.datasource.implementation.remote.dto

import com.example.translations.data.datasource.abstraction.remote.dto.TranslationDTO
import com.example.translations.data.datasource.abstraction.remote.dto.TranslationResponseDTO
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TranslationResponseDTOImpl(
    @SerializedName("translations")
    @Expose
    override val translations: List<TranslationDTOImpl>) : TranslationResponseDTO

data class TranslationDTOImpl(
    @SerializedName("text")
    @Expose
    override val text: String,
    @SerializedName("detectedLanguageCode")
    @Expose
    override val detectedLanguageCode: String
) : TranslationDTO