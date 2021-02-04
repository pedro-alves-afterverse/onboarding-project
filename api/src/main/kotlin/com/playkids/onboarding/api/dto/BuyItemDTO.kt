package com.playkids.onboarding.api.dto

import com.playkids.onboarding.core.model.ItemId

data class BuyItemDTO(
    val id: ItemId,
    val category: String,
    val currency: String
)
