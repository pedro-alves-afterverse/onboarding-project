package com.playkids.onboarding.data.listener.configuration

import com.github.jasync.sql.db.ConnectionPoolConfiguration
import com.github.jasync.sql.db.postgresql.PostgreSQLConnectionBuilder
import com.playkids.onboarding.data.listener.DataListener
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.sqs.SqsAsyncClient

object Configuration {
    private val credentialsProvider = DefaultCredentialsProvider.builder()
        .asyncCredentialUpdateEnabled(true)
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

    private val sqsClient = SqsAsyncClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.US_EAST_1)
        .build()

    val dataListener = DataListener(sqsClient)

}