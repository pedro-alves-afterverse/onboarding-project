package com.playkids.onboarding.data.listener.handler

object SQSEventHandler {
    suspend fun handle(message: String) {
        println("--------------------------------")
        println("SQS MESSAGE:")
        println(message)
        println("--------------------------------")

    }
}