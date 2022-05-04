package com.example.translations.framework.datasource.remote.dto

import com.example.translations.data.source.remote.dto.RemoteLanguageDTO
import com.example.translations.data.source.remote.dto.LanguagesResponseDTO
import com.google.gson.annotations.SerializedName

data class LanguagesResponseDtoImpl(
    @SerializedName("languages")
    override val languages: List<LanguageDtoImpl>
) : LanguagesResponseDTO

data class LanguageDtoImpl(
    @SerializedName("code")
    override val code: String,
    @SerializedName("name")
    override val name: String
) : RemoteLanguageDTO