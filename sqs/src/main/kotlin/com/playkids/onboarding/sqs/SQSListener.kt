package com.playkids.onboarding.sqs

import kotlinx.coroutines.Job

interface SQSListener {
    fun stop()
    fun start(): Job
}