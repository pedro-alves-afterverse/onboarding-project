package com.playkids.onboarding.sqs

import kotlinx.coroutines.Job

typealias MessageHandler = suspend (String, Map<String, String>) -> Unit

interface SQSListener {

    fun stop()
    fun start(): Job
    fun onMessageReceived(messageHandler: MessageHandler)
}