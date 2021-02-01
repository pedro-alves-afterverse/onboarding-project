package com.playkids.onboarding.dynamodb

import com.movile.kotlin.commons.dynamodb.*
import com.playkids.onboarding.core.model.SKU
import com.playkids.onboarding.core.model.SKUId
import com.playkids.onboarding.core.persistence.SKUDAO
import com.playkids.onboarding.dynamodb.extensions.itemOrNull
import com.typesafe.config.Config
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class DynamoDBSKUDAO(config: Config, private val dynamoDbClient: DynamoDbAsyncClient): SKUDAO {

    private val tableName = "onboarding-pedro-sku"

    override suspend fun create(sku: SKU) {
        dynamoDbClient.putItem {
            it.tableName(tableName)
                .conditionExpression("attribute_not_exists(${ID})")
                .item(sku.toItem())
        }.awaitRaiseException()
    }

    override suspend fun find(skuId: SKUId): SKU? =
        dynamoDbClient.getItem {
            it.tableName(tableName)
                .key(mapOf(ID to skuId.toAttributeValue()))
        }
            .awaitRaiseException()
            ?.itemOrNull()
            ?.toSKU()


    private fun SKU.toItem(): Map<String, AttributeValue> =
        mapOf(
             ID to id.toAttributeValue(),
             GEM to gem.toAttributeValue(),
             COIN to coin.toAttributeValue(),
             PRICE to price.toAttributeValue()
        )

    private fun Map<String, AttributeValue>.toSKU(): SKU =
        SKU(
            id = string(ID)!!,
            gem = int(GEM)!!,
            coin = int(COIN)!!,
            price = float(COIN)!!
        )

    companion object {
        private const val ID = "id"
        private const val GEM = "gem"
        private const val COIN = "coin"
        private const val PRICE = "price"
    }

}