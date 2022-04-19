package com.example.translations.data.datasource.abstraction.local.dto

interface WordDTO {
    val value: String
    val language: Long
    var queried: Boolean
    var id: Long
}