package com.playkids.onboarding.core.model

typealias ProfileId = String

enum class Regions {SA, CA, NA, AS, AF, EU, OC}

data class Profile(
    val id: ProfileId,
    val username: String,
    val items: List<String>,
    val gem: Int,
    val coin: Int,
    val moneySpent: Float,
    val region: Regions
)
