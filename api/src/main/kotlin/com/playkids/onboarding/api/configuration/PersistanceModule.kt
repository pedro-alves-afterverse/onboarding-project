package com.playkids.onboarding.api.configuration

import com.playkids.onboarding.dynamodb.DynamoDBItemDAO
import com.playkids.onboarding.dynamodb.DynamoDBSKUDAO
import com.typesafe.config.Config
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class PersistenceModule(config: Config, dynamoDbClient: DynamoDbAsyncClient) {
    val itemDAO = DynamoDBItemDAO(config, dynamoDbClient)
    val skuDAO = DynamoDBSKUDAO(config, dynamoDbClient)
}