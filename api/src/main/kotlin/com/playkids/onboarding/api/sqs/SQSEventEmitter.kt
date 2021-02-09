package com.playkids.onboarding.api.sqs

import com.playkids.onboarding.sqs.SQSEmitter
import com.playkids.onboarding.sqs.extensions.toMessageAttributeValue
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.SendMessageRequest

class SQSEventEmitter(
    private val sqs: SqsAsyncClient,
    private val queueUrl: String
): SQSEmitter {

    override fun sendEvent(message: String, attributes: Map<String, String>) = GlobalScope.launch {
        sqs.sendMessage(
            SendMessageRequest.builder()
                .queueUrl(queueUrl)
                .messageAttributes(attributes.mapValues { it.value.toMessageAttributeValue() })
                .messageBody(message)
                .build()
        ).await()
    }

}