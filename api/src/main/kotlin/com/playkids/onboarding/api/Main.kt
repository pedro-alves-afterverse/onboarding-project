package com.playkids.onboarding.api

import com.playkids.onboarding.api.configuration.Configuration.server
import kotlin.Exception

fun main() {
    try {
        server.start()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}