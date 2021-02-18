package com.playkids.onboarding.core.events

interface EventHandler {
    suspend fun handle(message: String, attributes: Map<String, String>)
}