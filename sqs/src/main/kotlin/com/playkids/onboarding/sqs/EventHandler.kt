package com.playkids.onboarding.sqs

interface EventHandler {
    suspend fun handle(message: String, attributes: Map<String, String>)
}