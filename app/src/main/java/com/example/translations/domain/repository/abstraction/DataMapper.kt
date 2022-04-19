package com.example.translations.domain.repository.abstraction

interface DataMapper<IN, OUT> {

    fun map(input: IN): OUT
}