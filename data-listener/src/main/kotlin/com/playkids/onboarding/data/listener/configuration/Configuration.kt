package com.playkids.onboarding.data.listener.configuration

import com.github.jasync.sql.db.ConnectionPoolConfiguration
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import com.playkids.onboarding.data.listener.handler.HandleProfile
import com.playkids.onboarding.data.listener.handler.SQSEventHandler
import com.playkids.onboarding.sqs.SQSEventListener
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

object Configuration {
    private val credentialsProvider = DefaultCredentialsProvider.builder()
        .asyncCredentialUpdateEnabled(true)
        .build()

    private val sqsClient = SqsAsyncClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.US_EAST_1)
        .build()

    private val postgresDB = PostgreSQLConnectionBuilder.createConnectionPool(
        ConnectionPoolConfiguration(
            host = "localhost",
            port = 5432,
            database = "onboarding",
            username = "postgres",
            password = "postgres"
        )
    )

    private val persistenceModule = PersistenceModule(postgresDB)

    private val profileHandler = HandleProfile(persistenceModule.profileDAO)

    private val eventHandler = SQSEventHandler(profileHandler)

    val eventListener = SQSEventListener(
        sqs = sqsClient,
        queueUrl = "https://sqs.us-east-1.amazonaws.com/027396584751/onboarding-pedro-data-transfer",
        maxNumberOfMessages = 5,
        messageHandler = eventHandler
    )

}