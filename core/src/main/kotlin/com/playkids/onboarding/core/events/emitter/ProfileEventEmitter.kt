package com.playkids.onboarding.core.events.emitter

import com.playkids.onboarding.core.dto.UpdateCurrencyDTO
import com.playkids.onboarding.core.model.Profile

interface ProfileEventEmitter {
    suspend fun handleInsert(profile: Profile)
    suspend fun handleUpdate(updateCurrencyDTO: UpdateCurrencyDTO)
}