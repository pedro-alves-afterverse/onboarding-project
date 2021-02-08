package com.playkids.onboarding.api.sqs

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

class SQSEventEmitter(
    val sqs: SqsAsyncClient
) {
    private val queueUrl = "https://sqs.us-east-1.amazonaws.com/027396584751/onboarding-pedro-data-transfer"

    fun sendEvent(message: String) = GlobalScope.launch {
        sqs.sendMessage(
            SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageBody(message)
                .build()
        ).await()
    }

}