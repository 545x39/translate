package com.example.translations.domain.entity

data class Word(
    val value: String,
    val language: Long,
    val id: Long = 0
)
