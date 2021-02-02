package com.playkids.onboarding.core.persistence

import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.util.ChooseValue

interface ProfileDAO {
    suspend fun create(profile: Profile)
    suspend fun find(id: ProfileId): Profile?
    suspend fun addItem(profileId: ProfileId, itemId: List<ItemId>)
    suspend fun updateCurrency(profileId: ProfileId, operation: String, currency: String, chooseValue: ChooseValue)
}