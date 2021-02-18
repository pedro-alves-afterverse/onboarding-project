package com.playkids.onboarding.core.events

import kotlinx.coroutines.Job

interface EventListener {
    fun stop()
    fun start(): Job
}