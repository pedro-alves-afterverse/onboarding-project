package com.playkids.onboarding.core.dto

import com.playkids.onboarding.core.model.Regions

data class CreateProfileDTO(
    val username: String,
    val region: Regions
)
