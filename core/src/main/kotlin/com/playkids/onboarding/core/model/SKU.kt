package com.playkids.onboarding.core.model

typealias SKUId = String

data class SKU(
    val id: SKUId,
    val gem: Int,
    val coin: Int,
    val price: Float
)