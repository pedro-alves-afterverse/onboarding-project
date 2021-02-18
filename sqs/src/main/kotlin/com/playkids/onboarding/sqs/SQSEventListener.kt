package com.playkids.onboarding.sqs

import com.playkids.onboarding.core.events.EventHandler
import com.playkids.onboarding.core.events.EventListener
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import java.util.concurrent.atomic.AtomicBoolean

class SQSEventListener(
    private val sqs: SqsAsyncClient,
    private val queueUrl: String,
    private val maxNumberOfMessages: Int,
    private val messageHandler: EventHandler
): EventListener {

    private val isRunning = AtomicBoolean(false)

    override fun stop() = isRunning.set(false)

    override fun start(): Job = GlobalScope.launch {
        isRunning.set(true)
        do {
            sqs.receiveMessage(
                ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(maxNumberOfMessages)
                    .messageAttributeNames(listOf("entity", "operation"))
                    .build()
            )
                .await()
                .messages()
                .forEach {
                    messageHandler.handle(it.body(), it.messageAttributes().mapValues { entry -> entry.value.stringValue() })
                    sqs.deleteMessage(
                        DeleteMessageRequest.builder()
                            .queueUrl(queueUrl)
                            .receiptHandle(it.receiptHandle())
                            .build()

                    )
                        .await()
                }
        } while (isRunning.get())
    }

}