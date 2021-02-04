package com.playkids.onboarding.api.dto

import com.playkids.onboarding.core.model.ItemId

data class AddItemDTO(
    val itemId: ItemId,
    val category: String
)
