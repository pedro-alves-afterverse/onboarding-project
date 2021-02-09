package com.playkids.onboarding.sqs

interface SQSHandler {
    suspend fun handle(message: String, attributes: Map<String, String>)
}