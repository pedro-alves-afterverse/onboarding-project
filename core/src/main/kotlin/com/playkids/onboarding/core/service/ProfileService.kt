package com.playkids.onboarding.core.service

import com.playkids.onboarding.core.model.ItemId
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.persistence.ProfileDAO

class ProfileService(
    private val profileDAO: ProfileDAO
) {
    suspend fun create(profile: Profile){
        profileDAO.create(profile)
    }

    suspend fun find(id: ProfileId): Profile?{
        return profileDAO.find(id)
    }

    suspend fun addItem(profileId: ProfileId, itemId: List<ItemId>){
        return profileDAO.addItem(profileId, itemId)
    }
}