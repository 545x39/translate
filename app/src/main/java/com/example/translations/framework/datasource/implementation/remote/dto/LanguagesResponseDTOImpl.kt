package com.example.translations.framework.datasource.implementation.remote.dto

import com.example.translations.data.datasource.abstraction.remote.dto.RemoteLanguageDTO
import com.example.translations.data.datasource.abstraction.remote.dto.LanguagesResponseDTO
import com.example.translations.domain.Language.CODE
import com.example.translations.domain.Language.NAME
import com.example.translations.framework.datasource.implementation.remote.LANGUAGES
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LanguagesResponseDTOImpl(
    @SerializedName(LANGUAGES)
    @Expose
    override val languages: List<LanguageDTOImpl>
) : LanguagesResponseDTO

data class LanguageDTOImpl(
    @SerializedName(CODE)
    @Expose
    override val code: String,
    @SerializedName(NAME)
    @Expose
    override val name: String
) : RemoteLanguageDTO