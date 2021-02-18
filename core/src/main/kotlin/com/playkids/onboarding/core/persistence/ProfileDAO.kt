package com.playkids.onboarding.core.persistence

import com.playkids.onboarding.core.model.ItemKey
import com.playkids.onboarding.core.model.Profile
import com.playkids.onboarding.core.model.ProfileId
import com.playkids.onboarding.core.util.Currencies


interface ProfileDAO {
    suspend fun create(profile: Profile)
    suspend fun find(id: ProfileId): Profile?
    suspend fun getItemsAndCurrency(id: ProfileId, currency: Currencies): Pair<List<ItemKey>, Int>?
    suspend fun addItem(profileId: ProfileId, item: List<String>)
    suspend fun updateCurrency(profileId: ProfileId, currency: Currencies, value: Number)
    suspend fun getProfileItems(id: ProfileId): List<ItemKey>?
    suspend fun getProfileCurrency(id: ProfileId): Map<Currencies, Int>?
}