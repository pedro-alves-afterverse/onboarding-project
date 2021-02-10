package com.playkids.onboarding.core.model

import com.playkids.onboarding.core.util.ItemCurrencies

typealias ItemId = String

data class Item(
    val category: String,
    val id: ItemId,
    val image: String,
    val currency: ItemCurrencies,
    val price: Int
)

data class ItemKey(
    private val category: String,
    private val id: ItemId
){
    fun getKey() = "$category:$id"

    companion object {
        fun fromString(key: String): ItemKey {
            val (category, id) = key.split(":")
            return ItemKey(category, id)
        }

        fun fromItem(item: Item): ItemKey {
            return ItemKey(item.category, item.id)
        }
    }
}