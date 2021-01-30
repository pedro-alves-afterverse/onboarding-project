package com.playkids.onboarding.dynamodb

import com.movile.kotlin.commons.dynamodb.awaitRaiseException
import com.movile.kotlin.commons.dynamodb.int
import com.movile.kotlin.commons.dynamodb.string
import com.movile.kotlin.commons.dynamodb.toAttributeValue
import com.playkids.onboarding.core.model.Item
import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.persistence.ItemDAO
import com.playkids.onboarding.dynamodb.extensions.batchGetItems
import com.playkids.onboarding.dynamodb.extensions.itemOrNull
import com.typesafe.config.Config
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue

class DynamoDBItemDAO(config: Config, private val dynamoDbClient: DynamoDbAsyncClient): ItemDAO {


    private val tableName = "onboarding-pedro-items"

    override suspend fun create(item: Item) {
        dynamoDbClient.putItem {
            it.tableName(tableName)
                .conditionExpression("attribute_not_exists($CATEGORY) AND attribute_not_exists($ID)")
                .item(item.toItem())
        }.awaitRaiseException()
    }

    override suspend fun find(category: String, itemId: ItemId): Item? =
        dynamoDbClient.getItem {
            it.tableName(tableName)
                .key(mapOf(CATEGORY to category.toAttributeValue(), ID to itemId.toAttributeValue()))
        }
            .awaitRaiseException()
            ?.itemOrNull()
            ?.toItem()

    override suspend fun findAllByCategory(category: String): List<Item>? {
        //TODO: batch get not working
        return dynamoDbClient.batchGetItems(listOf(mapOf(CATEGORY to category.toAttributeValue())), tableName).map { it.toItem() }
    }

    private fun Item.toItem(): Map<String, AttributeValue> =
        mapOf(
            CATEGORY to category.toAttributeValue(),
            ID to id.toAttributeValue(),
            IMAGE to image.toAttributeValue(),
            COIN_PRICE to coinPrice.toAttributeValue(),
            GEM_PRICE to gemPrice.toAttributeValue()
        )

    private fun Map<String, AttributeValue>.toItem(): Item =
        Item(
            category = string(CATEGORY)!!,
            id = string(ID)!!,
            image = string(IMAGE)!!,
            coinPrice = int(COIN_PRICE),
            gemPrice = int(GEM_PRICE)
        )


    companion object {
        private const val ID = "id"
        private const val CATEGORY = "category"
        private const val IMAGE = "image"
        private const val COIN_PRICE = "coinPrice"
        private const val GEM_PRICE = "gemPrice"
    }
}