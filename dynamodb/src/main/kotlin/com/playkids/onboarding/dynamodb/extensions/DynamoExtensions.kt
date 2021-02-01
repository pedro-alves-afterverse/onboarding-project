package com.playkids.onboarding.dynamodb.extensions

import software.amazon.awssdk.core.util.SdkAutoConstructMap
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse
import software.amazon.awssdk.services.dynamodb.model.QueryResponse


fun GetItemResponse.itemOrNull(): Map<String, AttributeValue>? =
    item()?.takeIf { it !is SdkAutoConstructMap }

fun QueryResponse.itemsOrNull(): List<Map<String, AttributeValue>>? =
    items()?.takeIf { it.size > 0 }
