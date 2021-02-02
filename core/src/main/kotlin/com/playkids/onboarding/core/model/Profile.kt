package com.playkids.onboarding.core.model

typealias ProfileId = String

data class Profile(
    val id: ProfileId,
    val username: String,
    val items: List<ItemId>,
    val gem: Int,
    val coin: Int,
    val moneySpent: Float
)
