package com.playkids.onboarding.core.model

typealias ItemId = String

data class Item(
    val category: String,
    val id: ItemId,
    val image: String,
    val coinPrice: Int?,
    val gemPrice: Int?
)
