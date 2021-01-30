package com.playkids.onboarding.dynamodb

import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.model.SKUId
import com.playkids.onboarding.core.persistence.SKUDAO
import com.typesafe.config.Config
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient

class DynamoDBSKUDAO(config: Config, private val dynamoDbClient: DynamoDbAsyncClient): SKUDAO {
    override suspend fun create(sku: SKU) {
        TODO("Not yet implemented")
    }

    override suspend fun find(skuId: SKUId) {
        TODO("Not yet implemented")
    }

}