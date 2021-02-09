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
