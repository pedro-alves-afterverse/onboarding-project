package com.playkids.onboarding.core.util

//https://stackoverflow.com/questions/60010298/how-can-i-convert-a-camel-case-string-to-snake-case-and-back-in-idiomatic-kotlin
val camelRegex = "(?<=[a-zA-Z])[A-Z]".toRegex()

fun String.camelToSnakeCase(): String {
    return camelRegex.replace(this) {
        "_${it.value}"
    }.toLowerCase()
}