package com.playkids.onboarding.core.events

import kotlinx.coroutines.Job

interface EventEmitter{

    fun sendEvent(message: String, attributes: Map<String, String>): Job
}