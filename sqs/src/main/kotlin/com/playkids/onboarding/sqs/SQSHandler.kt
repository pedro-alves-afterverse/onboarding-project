package com.playkids.onboarding.sqs

interface SQSHandler {
    fun handle(message: String, attributes: Map<String, String>)
}