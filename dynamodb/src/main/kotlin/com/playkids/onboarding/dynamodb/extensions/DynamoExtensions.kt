package com.playkids.onboarding.dynamodb.extensions

import com.movile.kotlin.commons.dynamodb.toAttributeValue
import com.playkids.onboarding.core.model.ItemKey
import software.amazon.awssdk.core.util.SdkAutoConstructMap
import software.amazon.awssdk.services.dynamodb.model.AttributeValue
import software.amazon.awssdk.services.dynamodb.model.GetItemResponse
import software.amazon.awssdk.services.dynamodb.model.QueryResponse

private val nullAttributeValue = AttributeValue.builder().nul(true).build()

fun GetItemResponse.itemOrNull(): Map<String, AttributeValue>? =
    item()?.takeIf { it !is SdkAutoConstructMap }

fun QueryResponse.itemsOrNull(): List<Map<String, AttributeValue>>? =
    items()?.takeIf { it.size > 0 }

fun List<String>?.toListAttributeValue(): AttributeValue =
    this?.let { list -> AttributeValue.builder().l(list.map { it.toAttributeValue() }).build() } ?: nullAttributeValue

fun Map<String, AttributeValue>.listOfString(attribute: String): List<String>? =
    this[attribute]?.l()?.map { it.s().toString() }

fun Map<String, AttributeValue>.listOfItemKey(attribute: String): List<ItemKey>? =
    this[attribute]?.l()?.map { ItemKey.fromString(it.s()) }
