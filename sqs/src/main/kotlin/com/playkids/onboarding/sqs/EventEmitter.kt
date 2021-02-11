package com.playkids.onboarding.sqs

import kotlinx.coroutines.Job

interface EventEmitter{

    fun sendEvent(message: String, attributes: Map<String, String>): Job
}