package com.playkids.onboarding.core.dto

import com.playkids.onboarding.core.model.ProfileId

data class UpdateCurrencyDTO(
    val profileId: ProfileId,
    val coin: Int? = null,
    val gem: Int? = null,
    val money: Float? = null
)
