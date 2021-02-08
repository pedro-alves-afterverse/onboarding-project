package com.playkids.onboarding.data.listener.handler

object SQSEventHandler {
    suspend fun handle(message: String) {
        println(message)
    }
}