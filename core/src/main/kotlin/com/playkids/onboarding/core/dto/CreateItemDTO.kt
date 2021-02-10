package com.playkids.onboarding.core.dto

import com.playkids.onboarding.core.util.ItemCurrencies

data class CreateItemDTO(
    val category: String,
    val image: String,
    val currency: ItemCurrencies,
    val price: Int
)
