package com.playkids.onboarding.dynamodb.extensions

import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import software.amazon.awssdk.core.async.SdkPublisher
import software.amazon.awssdk.core.util.SdkAutoConstructMap
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.BatchGetItemRequest
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse
import software.amazon.awssdk.services.dynamodb.model.KeysAndAttributes

const val BATCH_GET_MAX_ITEMS = 100

fun GetItemResponse.itemOrNull(): Map<String, AttributeValue>? =
    item()?.takeIf { it !is SdkAutoConstructMap }

fun <T : Any> SdkPublisher<T?>.asFlowNotNull() =
    this
        .filter { it != null }
        .map { it!! }
        .asFlow()

suspend fun DynamoDbAsyncClient.batchGetItems(
    keys: List<Map<String, AttributeValue>>,
    tableName: String
): List<Map<String, AttributeValue>> =
    keys.chunked(BATCH_GET_MAX_ITEMS)
        .map { buildBatchGetItemsRequest(it, tableName) }
        .map(::batchGetItemPaginator)
        .flatMap { publisher ->
            publisher
                .asFlowNotNull()
                .map { it.responses()[tableName]?.toList() ?: emptyList() }
                .toList()
        }
        .flatten()

private fun buildBatchGetItemsRequest(
    keys: List<Map<String, AttributeValue>>,
    tableName: String
): BatchGetItemRequest {
    val keysAndAttributes = KeysAndAttributes.builder()
        .keys(keys)
        .build()

    return BatchGetItemRequest.builder()
        .requestItems(mapOf(tableName to keysAndAttributes))
        .build()
}