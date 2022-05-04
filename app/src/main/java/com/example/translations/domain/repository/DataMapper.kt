package com.example.translations.domain.repository

interface DataMapper<IN, OUT> {

    fun map(input: IN): OUT
}