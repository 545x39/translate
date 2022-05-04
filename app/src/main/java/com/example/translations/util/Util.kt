package com.example.translations.util

inline fun <T> Boolean.ifTrue(block: () -> T): T? = when (this) {
    true -> block()
    false -> null
}

inline fun <T> Boolean.ifFalse(block: () -> T): T? = this.not().ifTrue(block)
