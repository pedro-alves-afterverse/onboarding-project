package com.playkids.onboarding.api.configuration

import com.playkids.onboarding.api.OnboardingApi
import com.playkids.onboarding.core.service.ItemService
import com.playkids.onboarding.core.service.ProfileService
import com.playkids.onboarding.core.service.SKUService
import com.typesafe.config.ConfigFactory
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

object Configuration {
    private const val APPLICATION = "onboarding"

    private val rootConfig = ConfigFactory.load()
    private val config = rootConfig.getConfig(APPLICATION)!!

    val serverPort = config.getInt("server-port")

    private val credentialsProvider = DefaultCredentialsProvider.builder()
        .asyncCredentialUpdateEnabled(true)
        .build()

    val dynamoDBClient = DynamoDbAsyncClient.builder()
        .credentialsProvider(credentialsProvider)
        .region(Region.US_EAST_1)
        .build()

    private val persistenceModule = PersistenceModule(
        config,
        dynamoDBClient
    )

    private val itemService = ItemService(
        itemDAO = persistenceModule.itemDAO
    )

    private val skuService = SKUService(
        skuDAO = persistenceModule.skuDAO
    )

    private val profileService = ProfileService(
        profileDAO = persistenceModule.profileDAO,
        skuDAO = persistenceModule.skuDAO
    )

    val server = OnboardingApi(serverPort, itemService, skuService, profileService)


}