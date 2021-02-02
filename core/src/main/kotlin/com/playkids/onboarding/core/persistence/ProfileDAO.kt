package com.playkids.onboarding.core.persistence

import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId

interface ProfileDAO {
    suspend fun create(profile: Profile)
    suspend fun find(id: ProfileId): Profile?
    suspend fun addItem(profileId: ProfileId, itemId: Int)
}