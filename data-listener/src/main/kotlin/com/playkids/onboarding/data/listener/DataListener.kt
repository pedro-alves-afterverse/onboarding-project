package com.playkids.onboarding.data.listener

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest
import java.util.concurrent.atomic.AtomicBoolean

typealias MessageHandler = suspend (String) -> Unit

class DataListener(
    val sqs: SqsAsyncClient
) {
    private val queueUrl = "https://sqs.us-east-1.amazonaws.com/027396584751/onboarding-pedro-data-transfer"
    private val maxNumberOfMessages = 5

    private lateinit var messageHandler: MessageHandler

    private val isRunning = AtomicBoolean(false)

    fun stop() = isRunning.set(false)

    fun start(): Job = GlobalScope.launch {
        isRunning.set(true)
        do {
            val messages = sqs.receiveMessage(
                ReceiveMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .maxNumberOfMessages(maxNumberOfMessages)
                    .build()
            )
                .await()
                .messages()
                .map {
                    it.body() to it.receiptHandle()
                }

            messages.forEach {
                messageHandler(it.first)
                sqs.deleteMessage(
                    DeleteMessageRequest.builder()
                        .queueUrl(queueUrl)
                        .receiptHandle(it.second)
                        .build()

                )
                    .await()
            }
        } while (isRunning.get())
    }

    fun onMessageReceived(messageHandler: MessageHandler){
        this.messageHandler = messageHandler
    }
}